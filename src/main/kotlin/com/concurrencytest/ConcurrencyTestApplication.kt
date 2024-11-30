package com.concurrencytest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ConcurrencyTestApplication

fun main(args: Array<String>) {
    runApplication<ConcurrencyTestApplication>(*args)
}
