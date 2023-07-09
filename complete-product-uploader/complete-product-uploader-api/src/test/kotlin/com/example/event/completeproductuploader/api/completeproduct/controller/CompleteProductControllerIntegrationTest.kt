package com.example.event.completeproductuploader.api.completeproduct.controller

import io.kotest.core.spec.style.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
class CompleteProductControllerIntegrationTest(
    @Autowired val webTestClient: WebTestClient
) : StringSpec({

    "수집 왼료된 상품들을 조회할 수 있다." {
        // when
        val responseSpec = webTestClient.get().uri("/api/v1/complete-products")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()

        // then
        responseSpec.expectStatus().isOk
    }
})
