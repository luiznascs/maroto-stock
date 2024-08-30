package com.javaday.maroto_stock.models.extensions

import java.text.NumberFormat
import java.util.Locale

fun Double.toReal(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return formatter.format(this)
}
