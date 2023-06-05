package io.backoffice.domain.analytics.repository

import io.backoffice.domain.analytics.model.res.UserActionRes
import io.backoffice.domain.auth.model.type.ActionType
import jakarta.transaction.Transactional
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
@Transactional
class UserActionJdbcRepository (
    private val jdbcTemplate: JdbcTemplate,
){

    private val userActionResRowMapper : RowMapper<UserActionRes> = RowMapper { rs, _ ->
        return@RowMapper UserActionRes(
            userId = rs.getString("USER_ID"),
            actionType = ActionType.valueOf(rs.getString("ACTION_TYPE")),
            count = rs.getLong("count")
        )
    }

    fun findUserActionStatistics(): List<UserActionRes> {
        val sql = """
            SELECT USER_ID, ACTION_TYPE, count(*) AS count
              FROM ACTIONS
             GROUP BY USER_ID, ACTION_TYPE
            """

        return jdbcTemplate.query(sql, userActionResRowMapper)
    }

}