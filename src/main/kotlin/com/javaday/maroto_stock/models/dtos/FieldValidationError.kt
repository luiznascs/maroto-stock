package com.javaday.maroto_stock.models.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class FieldValidationError(
    @JsonProperty("timestamp")
    val timestamp: Long,

    @JsonProperty("status")
    val status: Int,

    @JsonProperty("error")
    val error: String,

    @JsonProperty("message")
    val message: String,

    @JsonProperty("fieldErrors")
    val fieldErrors: Map<String, String>
)
