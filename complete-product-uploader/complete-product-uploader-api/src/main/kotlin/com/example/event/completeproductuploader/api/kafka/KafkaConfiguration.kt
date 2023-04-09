package com.example.event.completeproductuploader.api.kafka

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(KafkaAppProperties::class)
class KafkaConfiguration {

    @Bean
    fun imageProcessorStreamsReceiver(kafkaAppProperties: KafkaAppProperties) =
        KafkaReactiveConsumer<String, String>(
            kafkaAppProperties.bootstrapServers,
            kafkaAppProperties.imageProcessorInputTopic,
            kafkaAppProperties.consumerGroupId
        )
}