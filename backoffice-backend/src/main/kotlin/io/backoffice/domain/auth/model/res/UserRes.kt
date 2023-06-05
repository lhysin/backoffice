package io.backoffice.domain.auth.model.res

import io.swagger.v3.oas.annotations.media.Schema

class UserRes (

    @Schema(title = "userId")
    val userId: String,

    @Schema(title = "userName")
    val name: String,

    @Schema(title = "userEmail")
    val email: String,

    @Schema(title = "userContactNumber")
    val contactNumber: String,
)