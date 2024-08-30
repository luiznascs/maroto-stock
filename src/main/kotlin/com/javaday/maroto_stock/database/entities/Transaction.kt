package com.javaday.maroto_stock.database.entities

import jakarta.persistence.*

@Entity
data class Transaction(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val symbol: String,
    val quantity: Int,
    val price: Double,

    @Enumerated(EnumType.STRING)
    val type: TransactionType,

    @ManyToOne
    val user: User
)

enum class TransactionType {
    BUY, SELL
}
