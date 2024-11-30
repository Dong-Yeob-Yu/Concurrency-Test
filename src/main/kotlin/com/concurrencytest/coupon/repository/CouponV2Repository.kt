package com.concurrencytest.coupon.repository

import com.concurrencytest.coupon.domain.CouponV2
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CouponV2Repository : JpaRepository<CouponV2, Long> {
}