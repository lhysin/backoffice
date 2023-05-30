package io.backoffice.domain.auth.controller

import io.backoffice.common.provider.JwtTokenProvider
import io.backoffice.domain.auth.model.form.LoginForm
import io.backoffice.domain.auth.model.form.RefreshTokenForm
import io.backoffice.domain.auth.model.res.TokenRes
import io.backoffice.domain.auth.model.res.UserRes
import io.backoffice.domain.auth.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val userService: UserService,
    private val authenticationProvider: AuthenticationProvider,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @PostMapping("/login")
    fun login(@RequestBody loginForm: LoginForm): TokenRes {

        val authentication = authenticationProvider.authenticate(
            UsernamePasswordAuthenticationToken(loginForm.userId, loginForm.password)
        )

        if(!authentication.isAuthenticated) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
        }

        return TokenRes(
            jwtTokenProvider.generateAccessTokenByUserId(loginForm.userId),
            jwtTokenProvider.generateRefreshTokenByUserId(loginForm.userId)
        )
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody refreshTokenForm: RefreshTokenForm): TokenRes {

        val userId = jwtTokenProvider.extractUserIdFromToken(refreshTokenForm.refreshToken)

        return TokenRes(
            jwtTokenProvider.generateAccessTokenByUserId(userId),
            jwtTokenProvider.generateRefreshTokenByUserId(userId)
        )
    }

    @GetMapping("/me")
    fun me(): UserRes {
        val principal = SecurityContextHolder.getContext().authentication
            .principal as UserDetails
        return userService.findUserResById(principal.username)
    }
}