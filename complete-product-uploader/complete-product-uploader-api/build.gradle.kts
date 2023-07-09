repositories {
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

dependencies {
    implementation(project(":complete-product-uploader:complete-product-uploader-enum"))
    implementation(project(":tests:kotest"))

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo.spring30x:4.6.1") // 임베디드디비 스프링 3.0 테스트버전인가??

    implementation("org.springframework.kafka:spring-kafka")
    implementation("io.projectreactor.kafka:reactor-kafka")

    implementation("io.confluent:kafka-schema-registry-client:7.3.2")
    implementation("io.confluent:kafka-avro-serializer:7.3.2")
}