package com.concurrencytest.coupon.domain

import jakarta.persistence.*

@Entity
class CouponV2(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    var quantity: Int,
    @Version var version: Int? = 0,
)