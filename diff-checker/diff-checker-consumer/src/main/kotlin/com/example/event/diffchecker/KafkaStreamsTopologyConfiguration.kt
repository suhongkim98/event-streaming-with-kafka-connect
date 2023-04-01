package com.example.event.diffchecker

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.KStream
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
@EnableConfigurationProperties(KafkaAppProperties::class)
class KafkaStreamsTopologyConfiguration {

    @Bean("diffCheckTopology")
    fun diffCheckTopology(kafkaAppProperties: KafkaAppProperties): KafkaStreams {
        // env
        val bootstrapServers = kafkaAppProperties.bootstrapServers
        val schemaRegistryUrl = kafkaAppProperties.schemaRegistryUrl
        val applicationName = "example-mongo-diff-checker-app"
        val productStreamTopic = "example-stream.product"
        val upsertOrderTopic = "example-stream.product.upsert"
        val deleteOrderTopic = "example-stream.product.delete"

        // Generic Avro Serde for a generic Avro record type
        val serdeConfig = mapOf(
            AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to schemaRegistryUrl
        )
        val genericAvroRecordSerde = GenericAvroSerde()
        genericAvroRecordSerde.configure(serdeConfig, false)

        // props 설정
        val props = Properties()
        props[AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG] = schemaRegistryUrl
        props[StreamsConfig.APPLICATION_ID_CONFIG] = applicationName
        props[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        props[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.String()::class.java
        props[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = genericAvroRecordSerde::class.java

        // stream builder 설정
        val builder = StreamsBuilder()
        val productStream: KStream<String, GenericRecord> = builder.stream(productStreamTopic)

        productStream.filter { key, value ->
            val operationType = value.get("operationType")
            operationType.toString() == "insert" || operationType.toString() == "replace" || operationType.toString() == "update"
        }.to(upsertOrderTopic)

        productStream.filter { key, value ->
            val operationType = value.get("operationType")
            operationType.toString() == "delete"
        }.to(deleteOrderTopic)

        // topology 구성
        val streams = KafkaStreams(builder.build(), props)

        // start
        streams.start()
        return streams
    }
}