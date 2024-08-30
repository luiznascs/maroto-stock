package com.javaday.maroto_stock.models.dtos

import com.javaday.maroto_stock.database.entities.TransactionType

data class StockTransactionDTO(
    val symbol: String,
    val quantity: Int,
    val price: Double,
    val type: TransactionType
)