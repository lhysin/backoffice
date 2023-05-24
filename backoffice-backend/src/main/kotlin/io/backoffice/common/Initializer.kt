package io.backoffice.common

import io.backoffice.domain.auth.model.entity.User
import io.backoffice.domain.auth.model.type.UserRole
import io.backoffice.domain.auth.repository.UserRepository
import jakarta.annotation.PostConstruct
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class Initializer (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,

) {
    @PostConstruct
    fun createDefaultAdmin() {
        userRepository.save(
            User(
                userId = "admin",
                password = passwordEncoder.encode("admin"),
                passwordExpiredDate = LocalDateTime.now(),
                name = "admin",
                userRole = UserRole.ADMIN,
                email = "admin@gmail.com",
                contactNumber = "01012345678"
            )
        )
    }
}