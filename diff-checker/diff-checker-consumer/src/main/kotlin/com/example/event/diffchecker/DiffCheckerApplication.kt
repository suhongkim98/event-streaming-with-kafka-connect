package com.example.event.diffchecker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DiffCheckerApplication

fun main(args:Array<String>) {
    runApplication<DiffCheckerApplication>(*args)
}
