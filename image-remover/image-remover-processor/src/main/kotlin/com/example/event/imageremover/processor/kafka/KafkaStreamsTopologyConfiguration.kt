package com.example.event.imageremover.processor.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde
import org.apache.avro.Schema
import org.apache.avro.SchemaBuilder
import org.apache.avro.generic.GenericData
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
    @Bean("imageRemoverTopologyProperties")
    fun imageRemoverTopologyProperties(kafkaAppProperties: KafkaAppProperties): Properties {
        // env
        val bootstrapServers = kafkaAppProperties.bootstrapServers
        val schemaRegistryUrl = kafkaAppProperties.schemaRegistryUrl
        val applicationName = "image-remover-app"

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

        return props
    }
    @Bean("imageRemoverTopology")
    fun imageRemoverTopology(imageRemoverTopologyProperties: Properties, objectMapper: ObjectMapper): KafkaStreams {
        val productDeleteTopic = "example-stream.product.delete"
        val imageTopic = "example-stream.image-processor"

        // Avro 스키마 생성
        // SchemaBuilder로 스키마 정의 // 해당 레코드를 producing할 때 자동으로 스키마 레지스트리에 등록된다.
        val imageCollectorAvroSchema: Schema = SchemaBuilder.record("Image").fields()
            .requiredString("operationType")
            .requiredString("productId")
            .optionalString("collectedImageUrl")
            .optionalString("originalProduct")
            .optionalBoolean("removedSuccess")
            .endRecord()

        // stream builder 설정
        val builder = StreamsBuilder()
        val imageRemoverStream: KStream<String, GenericRecord> = builder.stream(productDeleteTopic)

        imageRemoverStream.mapValues { value ->
            val jsonNode = objectMapper.readTree(value.get("documentKey").toString())
            val productId = jsonNode.get("_id").toString()
            val isRemoved = mockDeleteFromS3(productId)

            val record = GenericData.Record(imageCollectorAvroSchema) // 내가 새로 정의한 avro 스키마를 가져다가 레코드 구성하고
            record.put("operationType", "delete")
            record.put("productId", productId)
            record.put("removedSuccess", isRemoved)

            record
        }.to(imageTopic)

        // topology 구성
        val streams = KafkaStreams(builder.build(), imageRemoverTopologyProperties)

        // start
        streams.start()
        return streams
    }

    fun mockDeleteFromS3(imageUrl: String): Boolean {
        Thread.sleep(10) // 잠깐 딜레이주고
        return true
    }
}