package io.backoffice.model.res

import java.time.LocalDateTime

class AdminRes (
    val adminId: String,
    val passwordExpired: Boolean,

    val createdBy: String? = null,
    val createdDate: LocalDateTime? = null,
    val lastModifiedBy: String? = null,
    val lastModifiedDate: LocalDateTime? = null,
)