repositories {
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

dependencies {
    implementation(project(":diff-checker:diff-checker-enum"))

    // https://mvnrepository.com/artifact/org.apache.kafka/kafka-streams
    // 카프카 브로커 지원하는지 버전 확인// https://mvnrepository.com/artifact/org.apache.kafka/connect-json
    implementation("org.apache.kafka:kafka-clients:3.4.0")
    implementation("org.apache.kafka:kafka-streams:3.4.0")

    // 카프카 버전 유의
    implementation("io.confluent:kafka-streams-avro-serde:7.3.2")
    implementation("io.confluent:kafka-schema-registry-client:7.3.2")
    implementation("io.confluent:kafka-avro-serializer:7.3.2")

    testImplementation("org.apache.kafka:kafka-streams-test-utils:3.4.0")

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("ch.qos.logback:logback-classic:1.4.6")
}