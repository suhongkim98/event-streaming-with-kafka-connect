package com.example.event.diffchecker

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.kafka")
class KafkaAppProperties {
    var bootstrapServers = ""
    var schemaRegistryUrl = ""
}