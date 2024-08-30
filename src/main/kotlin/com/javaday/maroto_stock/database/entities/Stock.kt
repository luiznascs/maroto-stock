package com.javaday.maroto_stock.database.entities

import jakarta.persistence.*

@Entity
data class Stock(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val symbol: String,

    @Column(nullable = false)
    var quantity: Int,

    @Column(nullable = false)
    var purchasePrice: Double,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User
)