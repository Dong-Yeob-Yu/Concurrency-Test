package com.concurrencytest.coupon.service

import com.concurrencytest.coupon.domain.Coupon
import com.concurrencytest.coupon.domain.CouponV2
import com.concurrencytest.coupon.repository.CouponRepository
import com.concurrencytest.coupon.repository.CouponV2Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CouponService (
    private val couponRepository: CouponRepository,
    private val couponV2Repository: CouponV2Repository,
) {

    fun getCoupon(couponId: Long): Coupon {
        return couponRepository.findById(couponId).orElseThrow { throw IllegalArgumentException("not found coupon ") }
    }

    @Transactional
    fun saveCoupon(coupon: Coupon){
        couponRepository.save(coupon)
    }

    @Transactional
    fun decreaseCoupon(couponId: Long){
        val coupon: Coupon = couponRepository.findById(couponId).orElseThrow { IllegalArgumentException("No coupon with id $couponId") }
        if(coupon.quantity != 0) {
            coupon.quantity -= 1
        }
    }

    @Transactional
    fun saveCouponV2(couponV2: CouponV2){
        couponV2Repository.save(couponV2)
    }

    @Transactional
    fun decreaseCouponV2(couponId: Long){
        val couponV2: CouponV2 = couponV2Repository.findById(couponId).orElseThrow { IllegalArgumentException("No coupon with id $couponId") }
        couponV2.quantity -= 1
    }

}