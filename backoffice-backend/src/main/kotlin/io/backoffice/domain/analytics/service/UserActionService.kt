package io.backoffice.domain.analytics.service

import io.backoffice.domain.analytics.model.res.UserActionRes
import io.backoffice.domain.analytics.repository.UserActionJdbcRepository
import org.springframework.stereotype.Service

@Service
class UserActionService(
    private val userActionJdbcRepository: UserActionJdbcRepository
) {

    fun findUserActionStatistics(): List<UserActionRes> {
        return userActionJdbcRepository.findUserActionStatistics()
    }
}