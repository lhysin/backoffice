package io.backoffice.domain.auth.controller

import io.backoffice.common.external.JsonPlaceholderClient
import io.backoffice.domain.auth.model.res.UserRes
import io.backoffice.domain.auth.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
    private val jsonPlaceholderClient: JsonPlaceholderClient,
) {

    @GetMapping("/{userId}")
    fun findById(@PathVariable("userId") userId: String): UserRes {
        jsonPlaceholderClient.getAllPosts();
        return userService.findUserResById(userId);
    }

    @GetMapping("/users")
    fun findAll(): List<UserRes> {
        return userService.findUserResAll();
    }

}