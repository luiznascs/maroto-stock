package com.javaday.maroto_stock.models.enums

import org.springframework.http.HttpStatus

enum class ServiceError(val status: HttpStatus, val message: String) {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "user.not.found"),
    INVALID_FIELD_DATA(HttpStatus.BAD_REQUEST, "invalid.field.data");
}