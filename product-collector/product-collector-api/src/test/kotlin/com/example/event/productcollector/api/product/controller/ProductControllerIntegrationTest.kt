package com.example.event.productcollector.api.product.controller

import com.example.event.productcollector.api.product.dto.RequestUpsertProductDto
import io.kotest.core.spec.style.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.body
import org.springframework.test.web.reactive.server.expectBody
import reactor.core.publisher.Mono

@SpringBootTest
@AutoConfigureWebTestClient
class ProductControllerIntegrationTest(
    @Autowired val webTestClient: WebTestClient
) : StringSpec({

    "상품을 등록할 수 있다." {
        // given
        val requestDto = RequestUpsertProductDto(
            productId = "product1",
            mallId = "애플",
            title = "맥북",
            content = "content",
            price = "10000",
            imageUrl = "https://test.com/image.jpg",
        )

        // when
        val responseSpec = webTestClient.post().uri("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(requestDto))
            .exchange()

        // then
        responseSpec.expectStatus().isCreated
        responseSpec.expectBody<String>().isEqualTo("UPSERT SUCCESS")
    }
})
