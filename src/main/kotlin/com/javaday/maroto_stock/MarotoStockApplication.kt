package com.javaday.maroto_stock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class MarotoStockApplication

fun main(args: Array<String>) {
	runApplication<MarotoStockApplication>(*args)
}
