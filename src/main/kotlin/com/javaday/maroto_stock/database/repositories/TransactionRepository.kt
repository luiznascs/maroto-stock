package com.javaday.maroto_stock.database.repositories

import com.javaday.maroto_stock.database.entities.Transaction
import com.javaday.maroto_stock.database.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository : JpaRepository<Transaction, Long> {
    fun findAllByUser(user: User):List<Transaction>
}