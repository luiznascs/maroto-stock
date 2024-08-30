package com.javaday.maroto_stock.modules.stock.service

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "stockPriceClient", url = "https://brapi.dev")
interface StockPriceClient {
    @GetMapping("/api/quote/{symbol}")
    fun getStockPrice(
        @RequestParam("token") token: String,
        @PathVariable symbol: String): StockPriceResponse
}

data class StockPriceResponse(val results: List<BrapiDto>)

data class BrapiDto(val symbol: String, val regularMarketPrice: Double)