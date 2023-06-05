package io.backoffice.domain.auth.controller

import io.backoffice.domain.auth.model.res.UserRes
import io.backoffice.domain.auth.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
) {

    @GetMapping("/{userId}")
    fun findById(@PathVariable("userId") userId: String): UserRes {
        return userService.findUserResById(userId);
    }

    @GetMapping
    fun findAllWithoutAdmin(): List<UserRes> {
        return userService.findAllWithoutAdmin();
    }

}