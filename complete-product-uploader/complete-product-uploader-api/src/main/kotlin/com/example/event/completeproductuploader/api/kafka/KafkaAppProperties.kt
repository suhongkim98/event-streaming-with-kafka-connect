package com.example.event.completeproductuploader.api.kafka

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.kafka")
class KafkaAppProperties {
    var bootstrapServers: String = ""
    var schemaRegistryUrl: String = ""
    var consumerGroupId: String = ""
    var imageProcessorInputTopic: String = ""
}