package com.example.event.imageremover.processor.kafka

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.kafka")
class KafkaAppProperties {
    lateinit var bootstrapServers: String
    lateinit var schemaRegistryUrl: String
}