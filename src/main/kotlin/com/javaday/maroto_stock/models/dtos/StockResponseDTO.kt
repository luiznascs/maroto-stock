package com.javaday.maroto_stock.models.dtos

data class StockResponseDTO(
    val symbol: String,
    val quantity: Int,
    val averagePurchasePrice: Double
)