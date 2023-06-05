package io.backoffice.domain.analytics.controller

import io.backoffice.domain.analytics.model.res.UserActionRes
import io.backoffice.domain.analytics.service.UserActionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/analytics")
class UserActionController(
    private val userActionService: UserActionService
) {

    @GetMapping("/user-action-statistic")
    fun findUserActionStatistics(): List<UserActionRes> {
        return userActionService.findUserActionStatistics()
    }

}