package com.javaday.maroto_stock.modules.stock.service



import com.javaday.maroto_stock.config.error.ServiceException
import com.javaday.maroto_stock.database.entities.Stock
import com.javaday.maroto_stock.database.entities.Transaction
import com.javaday.maroto_stock.database.entities.TransactionType
import com.javaday.maroto_stock.database.entities.User
import com.javaday.maroto_stock.database.repositories.StockRepository
import com.javaday.maroto_stock.database.repositories.TransactionRepository
import com.javaday.maroto_stock.database.repositories.UserRepository
import com.javaday.maroto_stock.models.dtos.StockResponseDTO
import com.javaday.maroto_stock.models.dtos.StockTransactionDTO
import com.javaday.maroto_stock.models.dtos.StockYieldDTO
import com.javaday.maroto_stock.models.enums.ServiceError
import com.javaday.maroto_stock.models.extensions.toReal
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import kotlin.random.Random

@Service
@Validated
class StockService(
    private val stockPriceClient: StockPriceClient,
    private val userRepository: UserRepository,
    private val stockRepository: StockRepository,
    private val transactionRepository: TransactionRepository,

    @Value("#{environment.token}")
    private val token: String
) {

    fun buyStock(userId: Long, stockSymbol: String, quantity: Int, purchasePrice: Double): ResponseEntity<StockResponseDTO> {
        val user = getUser(userId)

        val existingStock = stockRepository.findByUserAndSymbol(user, stockSymbol)

        val updatedStock = if (existingStock != null) {
            // Atualiza a quantidade e recalcula o preço médio de compra
            val newQuantity = existingStock.quantity + quantity
            val totalCost = (existingStock.purchasePrice * existingStock.quantity) + (purchasePrice * quantity)
            existingStock.purchasePrice = totalCost / newQuantity
            existingStock.quantity = newQuantity

            transactionRepository.save(Transaction(0, stockSymbol, quantity, purchasePrice, TransactionType.BUY, user))
            stockRepository.save(existingStock)
            existingStock
        } else {
            val newStock = Stock(
                symbol = stockSymbol,
                quantity = quantity,
                purchasePrice = purchasePrice,
                user = user
            )
            transactionRepository.save(Transaction(0, stockSymbol, quantity, purchasePrice, TransactionType.BUY, user))
            stockRepository.save(newStock)
            newStock
        }

        return ResponseEntity.ok(StockResponseDTO(
            symbol = updatedStock.symbol,
            quantity = updatedStock.quantity,
            averagePurchasePrice = updatedStock.purchasePrice
        ))
    }

    fun sellStock(userId: Long, symbol: String, quantity: Int, price: Double): ResponseEntity<StockResponseDTO> {
        val user = getUser(userId)
        val stock = stockRepository.findBySymbolAndUser(symbol, user)

        if (stock.quantity >= quantity) {
            val remainingQuantity = stock.quantity - quantity
            stock.quantity = remainingQuantity
            stockRepository.save(stock)

            transactionRepository.save(Transaction(0, symbol, quantity, price, TransactionType.SELL, user))

            val stockPurchaseDTO = StockResponseDTO(
                symbol = stock.symbol,
                quantity = stock.quantity,
                averagePurchasePrice = stock.purchasePrice
            )

            return ResponseEntity.ok(stockPurchaseDTO)
        } else {
            throw RuntimeException("Not enough stock to sell")
        }
    }

    private fun getUser(userId: Long): User =
        userRepository.findById(userId).orElseThrow { ServiceException(ServiceError.USER_NOT_FOUND) }

    fun calculateStockYield(userId: Long): ResponseEntity<List<StockYieldDTO>> {
        val user = userRepository.findById(userId).orElseThrow { Exception("User with id $userId not found") }
        val stocks = stockRepository.findAllByUser(user)

        return ResponseEntity.ok(stocks.map { stock ->
            val currentPrice = calculateCurrentPrice(stock.purchasePrice)
            val totalYield = calculateTotalYield(currentPrice, stock.quantity)
            //val currentPrice = getValueStock(stock.symbol)
            val profitOrLoss = calculateProfitOrLoss(currentPrice, stock.purchasePrice, stock.quantity)

            StockYieldDTO(
                symbol = stock.symbol,
                quantity = stock.quantity,
                purchasePrice = stock.purchasePrice,
                currentPrice = currentPrice.toReal(),
                totalYield = totalYield.toReal(),
                returnOnInvestment = profitOrLoss.toReal()
            )
        })
    }

    fun calculateCurrentPrice(purchasePrice: Double): Double =
        purchasePrice + Random.nextInt(-5, 6)


    fun calculateTotalYield(currentPrice: Double, quantity: Int): Double =
        currentPrice * quantity


    fun calculateProfitOrLoss(currentPrice: Double, purchasePrice: Double, quantity: Int): Double =
        (currentPrice - purchasePrice) * quantity

    private fun getValueStock(stockSymbol: String): Double {
        val response = stockPriceClient.getStockPrice(token, stockSymbol)
        return response.results.first().regularMarketPrice
    }

    fun getTransactions(userId: Long): ResponseEntity<List<StockTransactionDTO>> {
        val user = userRepository.findById(userId).orElseThrow { ServiceException(ServiceError.USER_NOT_FOUND) }
        val transactions = transactionRepository.findAllByUser(user)

        return ResponseEntity.ok(transactions.map { transaction ->
            StockTransactionDTO(
                symbol = transaction.symbol,
                quantity = transaction.quantity,
                price = transaction.price,
                type = transaction.type
            )
        })
    }
}
