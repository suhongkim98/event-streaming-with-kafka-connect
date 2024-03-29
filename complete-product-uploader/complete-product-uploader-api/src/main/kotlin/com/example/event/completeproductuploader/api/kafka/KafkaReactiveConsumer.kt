package com.example.event.completeproductuploader.api.kafka

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.kafka.receiver.KafkaReceiver
import reactor.kafka.receiver.ReceiverOptions
import reactor.kafka.receiver.ReceiverRecord
import java.util.*

class KafkaReactiveConsumer<K, V>(
    bootstrapServers: String,
    schemaRegistryUrl: String,
    topic: String,
    consumerGroupId: String,
    autoOffsetReset: String = "earliest"
) {

    companion object {
        private val logger = LoggerFactory.getLogger(KafkaReactiveConsumer::class.java)
    }

    private val receiver: Flux<ReceiverRecord<K, V>>

    init {
        val consumerProps = Properties()
        consumerProps[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        consumerProps[AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG] = schemaRegistryUrl
        consumerProps[ConsumerConfig.GROUP_ID_CONFIG] = consumerGroupId
        consumerProps[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        consumerProps[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = KafkaAvroDeserializer::class.java
        consumerProps[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = autoOffsetReset
        val consumerOptions = ReceiverOptions.create<K, V>(consumerProps).subscription(Collections.singleton(topic))

        receiver = KafkaReceiver.create(consumerOptions)
            .receive()
            .map {
                logger.info("Received message: $it")
                it.receiverOffset().commit()
                it
            }
    }

    fun consume() = receiver

}
