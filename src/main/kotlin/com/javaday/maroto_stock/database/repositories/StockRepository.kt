package com.javaday.maroto_stock.database.repositories

import com.javaday.maroto_stock.database.entities.Stock
import com.javaday.maroto_stock.database.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository : JpaRepository<Stock, Long> {
    fun findBySymbolAndUser(symbol: String, user: User): Stock
    fun findAllByUser(user: User): List<Stock>
    fun findByUserAndSymbol(user: User, stockSymbol: String): Stock?
}