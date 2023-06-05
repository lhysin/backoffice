package io.backoffice.common

import io.backoffice.common.handler.ActiveProfileHandler
import io.backoffice.domain.auth.model.entity.User
import io.backoffice.domain.auth.model.type.ActionType
import io.backoffice.domain.auth.model.type.UserRole
import io.backoffice.domain.auth.repository.UserRepository
import io.backoffice.domain.auth.service.ActionService
import jakarta.annotation.PostConstruct
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class Initializer(
    private val userRepository: UserRepository,
    private val actionService: ActionService,
    private val passwordEncoder: PasswordEncoder,
    private val activeProfileHandler: ActiveProfileHandler
) {
    @PostConstruct
    fun createDefaultAdmin() {
        if (userRepository.findById("admin").isEmpty) {
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

    @PostConstruct
    fun createTestData() {
        if (!activeProfileHandler.isDefaultProfile()) {
            return
        }

        val actionTypes = ActionType.values()

        (0..9).forEach {

            // create user
            val user = User(
                userId = "test-$it",
                password = passwordEncoder.encode("admin"),
                passwordExpiredDate = LocalDateTime.now(),
                name = "test-$it",
                userRole = UserRole.NORMAL,
                email = "test-$it@gmail.com",
                contactNumber = "019${it}2345678"
            )
            userRepository.save(user)

            // create action
            user.let {
                val numActions = (1..25).random()
                repeat(numActions) {
                    actionService.saveAction(
                        user.userId,
                        actionTypes.random()
                    )
                }
            }
        }
    }
}