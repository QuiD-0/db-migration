package com.quid.sinker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SinkerApplication

fun main(args: Array<String>) {
    runApplication<SinkerApplication>(*args)
}
