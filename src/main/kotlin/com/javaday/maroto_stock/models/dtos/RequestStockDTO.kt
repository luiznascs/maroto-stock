package com.javaday.maroto_stock.models.dtos

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class RequestStockDTO(
    @field:NotNull(message = "O ID do usuário é obrigatório")
    val userId: Long,
    @field:NotBlank(message = "O símbolo da ação é obrigatório")
    val symbol: String,
    @field:Min(value = 1, message = "A quantidade mínima de ações para vender é 1")
    val quantity: Int,
    @field:NotNull(message = "O valor de preço é obrigatorio")
    @field:Positive(message = "O preço deve ser um valor positivo")
    val price: Double
)