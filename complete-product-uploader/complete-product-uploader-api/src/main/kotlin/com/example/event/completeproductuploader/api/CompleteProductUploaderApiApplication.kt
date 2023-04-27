package com.example.event.completeproductuploader.api

import com.example.event.completeproductuploader.api.completeproduct.dto.RequestUpsertCompleteProductDto
import com.example.event.completeproductuploader.api.completeproduct.service.CompleteProductService
import com.example.event.completeproductuploader.api.kafka.KafkaReactiveConsumer
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.avro.generic.GenericRecord
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CompleteProductUploaderApiApplication(
    private val imageProcessorStreamsReceiver: KafkaReactiveConsumer<String, GenericRecord>,
    private val completeProductService: CompleteProductService,
    private val objectMapper: ObjectMapper,
) : CommandLineRunner {

    companion object {
        private val logger = LoggerFactory.getLogger(CompleteProductUploaderApiApplication::class.java)
    }

    override fun run(vararg args: String?) {
        imageProcessorStreamsReceiver.consume()
            .map {
                logger.info("key ${it.key()} value ${it.value()}")
                val record = it.value()
                val operationType = record.get("operationType").toString()

                if(operationType == "delete") {
                    completeProductService.deleteProduct(record.get("productId").toString())
                }
                if(operationType == "upsert") {
                    val jsonNode = objectMapper.readTree(record.get("fullDocument").toString())
                    completeProductService.upsertProduct(RequestUpsertCompleteProductDto(
                        productId = jsonNode.get("productId").toString(),
                        mallId = jsonNode.get("mallId").toString(),
                        content = jsonNode.get("content").toString(),
                        imageUrl = record.get("collectedImageUrl").toString(),
                        price = jsonNode.get("price").toString(),
                        title = jsonNode.get("title").toString(),
                    ))
                }
            }
            .subscribe()
    }
}

fun main(args:Array<String>)
{
    runApplication<CompleteProductUploaderApiApplication>(*args)
}