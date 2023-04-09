package com.example.event.completeproductuploader.api

import com.example.event.completeproductuploader.api.kafka.KafkaReactiveConsumer
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CompleteProductUploaderApiApplication(
    private val imageProcessorStreamsReceiver: KafkaReactiveConsumer<String, String>,
) : CommandLineRunner {

    companion object {
        private val logger = LoggerFactory.getLogger(CompleteProductUploaderApiApplication::class.java)
    }

    override fun run(vararg args: String?) {
        imageProcessorStreamsReceiver.consume()
            .map {
                logger.info("key ${it.key()} value ${it.value()}")
                // TODO : 받아서 몽고디비에 반영 구현
                it
            }
            .subscribe()
    }
}

fun main(args:Array<String>)
{
    runApplication<CompleteProductUploaderApiApplication>(*args)
}