package com.example.event.completeproductuploader.api.kafka

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.kafka")
class KafkaAppProperties {
    lateinit var bootstrapServers: String
    lateinit var schemaRegistryUrl: String
    lateinit var consumerGroupId: String
    lateinit var imageProcessorInputTopic: String
}