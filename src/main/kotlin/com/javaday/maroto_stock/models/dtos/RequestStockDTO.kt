package com.javaday.maroto_stock.models.dtos

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive


data class RequestStockDTO(
    @field:NotNull(message = "O ID do usuário é obrigatório")
    val userId: Long?,

    @field:NotBlank(message = "Name must not be blank")
    val symbol: String,

    @field:Min(1, message = "Quantity must be at least 1")
    val quantity: Int,

    @field:Positive(message = "Price must be a positive value")
    val price: Double
)