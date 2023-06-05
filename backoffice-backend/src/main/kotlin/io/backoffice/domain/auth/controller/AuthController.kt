package io.backoffice.domain.auth.controller

import io.backoffice.common.provider.JwtTokenProvider
import io.backoffice.domain.auth.model.form.LoginForm
import io.backoffice.domain.auth.model.form.RefreshTokenForm
import io.backoffice.domain.auth.model.res.TokenRes
import io.backoffice.domain.auth.model.res.UserRes
import io.backoffice.domain.auth.model.type.ActionType
import io.backoffice.domain.auth.service.ActionService
import io.backoffice.domain.auth.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@Validated
@RequestMapping("/api/v1/auth")
class AuthController(
    private val userService: UserService,
    private val authenticationProvider: AuthenticationProvider,
    private val jwtTokenProvider: JwtTokenProvider,
    private val actionService: ActionService
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody loginForm: LoginForm): TokenRes {

        val authentication = authenticationProvider.authenticate(
            UsernamePasswordAuthenticationToken(loginForm.userId, loginForm.password)
        )

        if(!authentication.isAuthenticated) {
            actionService.saveAction(
                loginForm.userId,
                ActionType.LOGIN_FAIL
            )
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
        }

        actionService.saveAction(
            loginForm.userId,
            ActionType.LOGIN
        )

        return TokenRes(
            jwtTokenProvider.generateAccessTokenByUserId(loginForm.userId),
            jwtTokenProvider.generateRefreshTokenByUserId(loginForm.userId)
        )
    }

    @PostMapping("/refresh")
    fun refresh(@Valid @RequestBody refreshTokenForm: RefreshTokenForm): TokenRes {

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