package io.backoffice

import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

private val logger = KotlinLogging.logger {}

@SpringBootTest(
    classes = [BackOfficeApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApiTests (

    @Autowired
    var webTestClient: WebTestClient,
) {

    @Test
    fun adminApiTest() {

        webTestClient.get()
            .uri("/api/v1/users")
            .exchange()
            .expectStatus()
            .isOk()

    }

}
