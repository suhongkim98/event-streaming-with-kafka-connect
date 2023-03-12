
dependencies {
	implementation(project(":product-collector:product-collector-enum"))
	implementation(project(":tests:kotest"))

	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
	implementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo.spring30x:4.6.1") // 임베디드디비 스프링 3.0 테스트버전인가??
}
