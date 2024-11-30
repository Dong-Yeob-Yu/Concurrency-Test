package com.concurrencytest.coupon.service

import com.concurrencytest.coupon.domain.Coupon
import com.concurrencytest.coupon.domain.CouponV2
import com.concurrencytest.coupon.repository.CouponRepository
import com.concurrencytest.coupon.repository.CouponV2Repository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@SpringBootTest
class CouponServiceTest @Autowired constructor(
    private val couponService: CouponService,
    private val couponRepository: CouponRepository,
    private val couponV2Repository: CouponV2Repository,
){

    @BeforeEach
    fun setUp(){
        couponRepository.deleteAll()
        couponV2Repository.deleteAll()
        val coupon = Coupon(null, 1000)
        val couponV2 = CouponV2(null, 1000, null)
        couponService.saveCoupon(coupon)
        couponService.saveCouponV2(couponV2)
    }

    @DisplayName("쿠폰 갯수가 감소하는지 확인한다.")
    @Test
    @Transactional
    fun decreaseCoupon(){
        //when
        val findCoupon: Coupon = couponRepository.findById(1).orElseThrow { IllegalArgumentException("Not Found Coupon") }
        Assertions.assertThat(findCoupon.id).isEqualTo(1L)
        Assertions.assertThat(findCoupon.quantity).isEqualTo(10)

        //then
        couponService.decreaseCoupon(findCoupon.id!!)

    }
    
    @DisplayName("DB락이 없을떄 : 10개의 쓰레드로 1000번의 감소 요청이 들어가면 어떻게 되는지 확인한다.")
    @Test
    fun decreaseCouponNotConcurrency(){

        val findCoupon: Coupon = couponRepository.findById(1L).orElseThrow { IllegalArgumentException("Not Found Coupon") }

        val threadPool = Executors.newFixedThreadPool(10)

        //when
        for ( index in 1..1000) {
            threadPool.submit {
                couponService.decreaseCoupon(findCoupon.id!!)
            }
        }
        //then
        threadPool.shutdown()
        threadPool.awaitTermination(6, TimeUnit.SECONDS)

        val coupon: Coupon = couponRepository.findById(1L).orElseThrow()
        println("quantity is ${coupon.quantity}")

        Assertions.assertThat(coupon.quantity).isNotEqualTo(0) // true
    }
    
    @DisplayName("낙관적 락을 했을때 : 10개의 쓰레드로 1000번의 감소 요청을 넣는다.")
    @Test
    fun decreaseCouponConcurrency(){
        //given
        val findCoupon: CouponV2 = couponV2Repository.findById(1L).orElseThrow { IllegalArgumentException("Not Found Coupon") }

        val threadPool = Executors.newFixedThreadPool(10)

        //when
        for ( index in 1..1000) {
            threadPool.submit {
                couponService.decreaseCouponV2(findCoupon.id!!)
            }
        }
        //then
        threadPool.shutdown()
        threadPool.awaitTermination(6, TimeUnit.SECONDS)

        val coupon: CouponV2 = couponV2Repository.findById(1L).orElseThrow()
        println("quantity is ${coupon.quantity}")

        Assertions.assertThat(coupon.quantity).isNotEqualTo(0) // true
    
    }
}