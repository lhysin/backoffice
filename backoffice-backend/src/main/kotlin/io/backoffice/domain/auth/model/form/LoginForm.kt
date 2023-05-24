package io.backoffice.domain.auth.model.form

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

class LoginForm (

    @Schema(title = "로그인 아이디")
    @NotBlank
    val userId: String,

    @Schema(title = "로그인 비밀번호")
    @NotBlank
    val password: String,

)