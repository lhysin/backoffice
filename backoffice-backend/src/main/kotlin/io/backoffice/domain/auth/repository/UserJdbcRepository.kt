package io.backoffice.domain.auth.repository

import io.backoffice.domain.auth.converter.UserResConverter
import io.backoffice.domain.auth.model.res.UserRes
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException

@Repository
@Transactional
class UserJdbcRepository (
    private val userResConverter: UserResConverter,
    private val jdbcTemplate: JdbcTemplate,
){

    private val userRowMapper: RowMapper<UserRes> = RowMapper { rs, _ ->
        return@RowMapper UserRes(
            userId = rs.getString("userId"),
            name = rs.getString("name"),
            email = rs.getString("email"),
            contactNumber = rs.getString("contactNumber"),
        )
    }

    fun findByIdWithJDBC(userId: String): UserRes {
        val sql = "SELECT * FROM USERS WHERE id = ?"
        val userRow: List<UserRes> = jdbcTemplate.query(sql, arrayOf(userId), userRowMapper)

        return userRow.firstOrNull()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

}