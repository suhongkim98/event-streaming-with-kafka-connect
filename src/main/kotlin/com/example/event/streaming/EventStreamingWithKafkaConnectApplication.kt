package com.example.event.streaming

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EventStreamingWithKafkaConnectApplication

fun main(args: Array<String>) {
    runApplication<EventStreamingWithKafkaConnectApplication>(*args)
}
