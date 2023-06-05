package io.backoffice.domain.auth.model.res

import io.swagger.v3.oas.annotations.media.Schema

class TokenRes (

    @Schema(title = "accessToken")
    val accessToken: String,

    @Schema(title = "refreshToken")
    val refreshToken: String,
)