package com.concurrencytest.coupon.controller

import com.concurrencytest.coupon.domain.Coupon
import com.concurrencytest.coupon.service.CouponService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/coupon")
class CouponController(
    private val couponService: CouponService,
) {

    @GetMapping
    fun getCoupon(@RequestParam("couponId") couponId : Long): Coupon {
        return couponService.getCoupon(couponId)
    }

    @PostMapping("/{couponId}")
    fun redeemCoupon(@PathVariable("couponId") couponId : Long) {
        couponService.decreaseCoupon(couponId)
    }
}