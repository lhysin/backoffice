package io.backoffice.domain.analytics.model.res

import io.backoffice.domain.auth.model.type.ActionType
import io.swagger.v3.oas.annotations.media.Schema

class UserActionRes (

    @Schema(title = "userId")
    val userId: String,

    @Schema(title = "actionType")
    val actionType: ActionType,

    @Schema(title = "count")
    val count: Long,
)