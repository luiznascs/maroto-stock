package com.javaday.maroto_stock.database.repositories

import com.javaday.maroto_stock.database.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>