package io.backoffice.common.filter

import io.backoffice.common.provider.JwtTokenProvider
import io.backoffice.domain.auth.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtRequestFilter (
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val token = request.getHeader(HttpHeaders.AUTHORIZATION)
            ?.removePrefix("Bearer ")
            ?.trim()

        if (!token.isNullOrEmpty() && jwtTokenProvider.isTokenExpired(token)) {

            val userDetails = userService.loadUserByUsername(
                jwtTokenProvider.extractUserIdFromToken(token)
            )

            SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.authorities
            )
        }

        filterChain.doFilter(request, response)
    }
}