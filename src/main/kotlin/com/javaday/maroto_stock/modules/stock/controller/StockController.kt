package com.javaday.maroto_stock.modules.stock.controller

import com.javaday.maroto_stock.models.dtos.RequestStockDTO
import com.javaday.maroto_stock.models.dtos.StockResponseDTO
import com.javaday.maroto_stock.models.dtos.StockTransactionDTO
import com.javaday.maroto_stock.models.dtos.StockYieldDTO
import com.javaday.maroto_stock.modules.stock.service.StockService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/stocks")
@Tag(name = "Stock", description = "Endpoints for managing Book")
class StockController(private val stockService: StockService) {

    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "buy stock by user"
    )
    @PostMapping("/buy")
    fun buyStock(
        @RequestParam userId: Long,
        @RequestParam symbol: String,
        @RequestParam quantity: Int,
        @RequestParam price: Double
    ):  ResponseEntity<StockResponseDTO> =
        stockService.buyStock(userId, symbol, quantity, price)


    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "sell stock by user"
    )
    @PostMapping("/sell")
    fun sellStock(
        @Validated @RequestBody request: RequestStockDTO
    ): ResponseEntity<StockResponseDTO> =
        stockService.sellStock(request.userId, request.symbol, request.quantity, request.price)

    @Operation(summary = "Calculate Stock Yield", description = "Calculates the yield of a stock based on its purchase price and current market value.")
    @GetMapping("/yield")
    fun calculateStockYield(@RequestParam userId: Long):  ResponseEntity<List<StockYieldDTO>> {
        return stockService.calculateStockYield(userId)
    }

    @Operation(summary = "transaction history by user", description = "Transaction history by user")
    @GetMapping("/transactions")
    fun getTransactions(@RequestParam userId: Long):  ResponseEntity<List<StockTransactionDTO>> {
        return stockService.getTransactions(userId)
    }
}