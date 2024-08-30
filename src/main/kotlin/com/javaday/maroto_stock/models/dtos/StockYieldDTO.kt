package com.javaday.maroto_stock.models.dtos

data class StockYieldDTO(
    val symbol: String,
    val quantity: Int,
    val purchasePrice: Double,
    val currentPrice: String,
    val totalYield: String,
    val returnOnInvestment: String,
)