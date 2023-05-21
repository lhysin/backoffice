package io.backoffice.service

import io.backoffice.converter.AdminResConverter
import io.backoffice.model.res.AdminRes
import io.backoffice.repository.AdminRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
@Transactional
class AdminService (
    private val adminRepository: AdminRepository,
    private val adminResConverter: AdminResConverter,
) {

    fun findById(adminId: String): AdminRes {
        return adminRepository.findByIdOrNull(adminId)
            ?.let { adminResConverter.convert(it) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    fun findAll(): List<AdminRes> {
        return adminRepository.findAll()
            .map { adminResConverter.convert(it) }
    }

}