package com.example.event.imageremover.processor.kafka

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.kafka")
class KafkaAppProperties {
    var bootstrapServers: String = ""
    var schemaRegistryUrl: String = ""
}