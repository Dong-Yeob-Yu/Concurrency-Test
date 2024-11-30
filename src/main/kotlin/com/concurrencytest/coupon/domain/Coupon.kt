package com.concurrencytest.coupon.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Coupon(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    var quantity: Int,
)