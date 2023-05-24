package io.backoffice.domain.auth.model.form

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

class RefreshTokenForm (

    @Schema(title = "Refresh Token")
    @NotBlank
    val refreshToken: String

)