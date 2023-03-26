package com.example.event.diffchecker

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.KStream
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde
import java.util.*

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig
import org.apache.avro.generic.GenericRecord

/**
 * 카프카 스트림즈로 Avro Schema를 GenericRecord으로 받아 처리하는 예제
 */
fun main(args:Array<String>) {
    // env
    val applicationName = "mongo-diff-checker-app"
    val bootstrapServers = "localhost:9092"
    val productStreamTopic = "example-stream.product"
    val upsertProductTopic = "example-stream.product.upsert"
    val deleteProductTopic = "example-stream.product.delete"
    val schemaRegistryUrl = "http://localhost:8081"

    // Generic Avro Serde for a generic Avro record type
    val serdeConfig = mapOf(
        AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to schemaRegistryUrl
    )
    val genericAvroRecordSerde = GenericAvroSerde()
    genericAvroRecordSerde.configure(serdeConfig, false)

    // streams props 설정
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
    }.to(upsertProductTopic)

    productStream.filter { key, value ->
        val operationType = value.get("operationType")
        operationType.toString() == "delete"
    }.to(deleteProductTopic)

    // topology 구성
    val streams = KafkaStreams(builder.build(), props)

    // start
    streams.start()
}
