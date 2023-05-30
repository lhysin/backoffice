package io.backoffice.common.provider

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import java.util.*

private val logger = KotlinLogging.logger {}

@Component
class JwtTokenProvider {

    companion object {
        private const val ACCESS_TOKEN_VALIDITY_MS = 60 * 60 * 1000 // 60 minutes
        private const val REFRESH_TOKEN_VALIDITY_MS = 7 * 24 * 60 * 60 * 1000 // 7 days
    }

    private val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generateAccessTokenByUserId(userId : String): String {
        return Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_MS))
            .signWith(secretKey)
            .compact()
    }

    fun generateRefreshTokenByUserId(userId : String): String {
        return Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY_MS))
            .signWith(secretKey)
            .compact()
    }

    @Throws(ResponseStatusException::class)
    fun extractUserIdFromToken(token: String) : String {

        return try {

            if(token.isEmpty()) {
                throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
            }

            val claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body

            val expirationDate = claims.expiration

            if (expirationDate.before(Date())) {
                throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
            }

            claims.subject
        } catch (e: Exception) {
            logger.error(e.message)
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
        }
    }


}