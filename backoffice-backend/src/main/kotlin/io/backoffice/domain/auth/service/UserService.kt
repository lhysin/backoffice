package io.backoffice.domain.auth.service

import io.backoffice.domain.auth.converter.UserResConverter
import io.backoffice.domain.auth.model.entity.User
import io.backoffice.domain.auth.model.res.UserRes
import io.backoffice.domain.auth.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class UserService (
    private val userRepository: UserRepository,
    private val userResConverter: UserResConverter,
) : UserDetailsService {

    fun findById(userId: String): User {
        return userRepository.findByIdOrNull(userId)
            //?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
            ?: throw EntityNotFoundException("User not found")
    }

    fun findUserResById(userId: String): UserRes {
        return this.findById(userId)
            .let { userResConverter.convert(it) }
    }

    fun findUserResAll(): List<UserRes> {
        return userRepository.findAll()
            .map { userResConverter.convert(it) }
    }

    fun deleteByUserId(userId: String) {
        val user = userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException("User not found") }

        user.deletedBy = "admin" // 삭제한 사용자명 설정
        user.deletedDate = LocalDateTime.now() // 삭제 일시 설정

        userRepository.save(user)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUserIdAndDeletedDateIsNullAndDeletedByIsNull(username)
            ?: throw EntityNotFoundException("User not found")

        return org.springframework.security.core.userdetails.User(
            user.userId,
            user.password,
            emptyList()
        )
    }

}