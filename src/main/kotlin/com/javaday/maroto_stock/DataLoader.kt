package com.javaday.maroto_stock

import com.javaday.maroto_stock.database.entities.Stock
import com.javaday.maroto_stock.database.entities.User
import com.javaday.maroto_stock.database.repositories.StockRepository
import com.javaday.maroto_stock.database.repositories.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataLoader(private val userRepository: UserRepository, private val stockRepository: StockRepository) :
    CommandLineRunner {
    override fun run(vararg args: String?) {
        val user1 = User(1, name = "taylsonmartinez")
        val user2 = User(2, name = "João")
        userRepository.saveAll(listOf(user1, user2))

        val stock1 = Stock(1, symbol = "ITSA4", quantity = 100, 8.50, user = user1)
        val stock2 = Stock(2, symbol = "EMBR3", quantity = 100, 27.00, user = user2)
        stockRepository.saveAll(listOf(stock1, stock2))
    }
}