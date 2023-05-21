package io.backoffice.service

import io.backoffice.converter.AdminResConverter
import io.backoffice.model.entity.Admin
import io.backoffice.model.res.AdminRes
import io.backoffice.repository.AdminRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
@Transactional
class AdminJdbcService (
    private val adminResConverter: AdminResConverter,
    private val jdbcTemplate: JdbcTemplate,
) {

    private val adminRowMapper: RowMapper<Admin> = RowMapper { rs, _ ->
        return@RowMapper Admin(
            adminId = rs.getString("adminId"),
            password = rs.getString("password"),
            passwordExpired = rs.getBoolean("passwordExpired"),
            createdBy = rs.getString("createdBy"),
            createdDate = rs.getTimestamp("createdDate")?.toLocalDateTime(),
            lastModifiedBy = rs.getString("lastModifiedBy"),
            lastModifiedDate = rs.getTimestamp("lastModifiedDate")?.toLocalDateTime()
        )
    }

    fun findByIdWithJDBC(adminId: String): AdminRes {
        val sql = "SELECT * FROM admin WHERE id = ?"
        val adminRow: List<Admin> = jdbcTemplate.query(sql, arrayOf(adminId), adminRowMapper)

        return adminRow.firstOrNull()?.let { adminResConverter.convert(it) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

}