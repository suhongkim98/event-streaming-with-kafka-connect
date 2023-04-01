package com.example.event.diffchecker

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.kafka")
class KafkaAppProperties {
    lateinit var bootstrapServers: String
    lateinit var schemaRegistryUrl: String
}