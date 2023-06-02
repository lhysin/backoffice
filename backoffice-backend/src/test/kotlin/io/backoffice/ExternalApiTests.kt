package io.backoffice

import io.backoffice.common.external.JsonPlaceholderClient
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

private val logger = KotlinLogging.logger {}

@SpringBootTest(
    classes = [BackOfficeApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExternalApiTests (
    @Autowired
    var jsonPlaceholderClient: JsonPlaceholderClient,
) {

    @Test
    fun adminApiTest() {
        logger.debug(jsonPlaceholderClient.getPost(1).toString())
    }

}