package io.backoffice.domain.auth.controller

import io.backoffice.common.provider.JwtTokenProvider
import io.backoffice.domain.auth.model.form.LoginForm
import io.backoffice.domain.auth.model.form.RefreshTokenForm
import io.backoffice.domain.auth.model.res.TokenRes
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
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

        if(jwtTokenProvider.isTokenExpired(refreshTokenForm.refreshToken)) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
        }

        val userId = jwtTokenProvider.extractUserIdFromToken(refreshTokenForm.refreshToken)

        return TokenRes(
            jwtTokenProvider.generateAccessTokenByUserId(userId),
            jwtTokenProvider.generateRefreshTokenByUserId(userId)
        )
    }
}