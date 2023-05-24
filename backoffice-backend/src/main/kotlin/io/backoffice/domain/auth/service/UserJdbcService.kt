package io.backoffice.domain.auth.service

import io.backoffice.domain.auth.converter.UserResConverter
import io.backoffice.domain.auth.model.entity.User
import io.backoffice.domain.auth.model.res.UserRes
import io.backoffice.domain.auth.model.type.UserRole
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
@Transactional
class UserJdbcService(
    private val userResConverter: UserResConverter,
    private val jdbcTemplate: JdbcTemplate,
){

    private val userRowMapper: RowMapper<User> = RowMapper { rs, _ ->
        return@RowMapper User(
            userId = rs.getString("userId"),
            password = rs.getString("password"),
            passwordExpiredDate = rs.getTimestamp("passwordExpiredDate")?.toLocalDateTime()?: LocalDateTime.now(),
            name = rs.getString("name"),
            email = rs.getString("email"),
            userRole = UserRole.valueOf(rs.getString("userRole")),
            contactNumber = rs.getString("contactNumber"),
            createdBy = rs.getString("createdBy"),
            createdDate = rs.getTimestamp("createdDate")?.toLocalDateTime(),
            lastModifiedBy = rs.getString("lastModifiedBy"),
            lastModifiedDate = rs.getTimestamp("lastModifiedDate")?.toLocalDateTime()
        )
    }

    fun findByIdWithJDBC(userId: String): UserRes {
        val sql = "SELECT * FROM USERS WHERE id = ?"
        val userRow: List<User> = jdbcTemplate.query(sql, arrayOf(userId), userRowMapper)

        return userRow.firstOrNull()?.let { userResConverter.convert(it) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

}