package io.backoffice.config

import io.backoffice.common.filter.JwtRequestFilter
import io.backoffice.common.handler.ActiveProfileHandler
import io.backoffice.domain.auth.service.UserService
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val userService: UserService,
    private val jwtRequestFilter: JwtRequestFilter,
    private val activeProfileHandler: ActiveProfileHandler
) {

    @Bean
    fun passwordEncoder() : PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userService)
        authenticationProvider.setPasswordEncoder(passwordEncoder())
        return authenticationProvider
    }

    @Bean
    fun securityFilterChain(http : HttpSecurity) : SecurityFilterChain {

        /**
         * https://stackoverflow.com/questions/75460831/spring-boot-3-security-cannot-access-h2-console-403
         *
         * access /h2-console
         */
        if(activeProfileHandler.isDefaultProfile()) {
            http
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(PathRequest.toH2Console()).permitAll()
        }

        return http

            .cors()
            .and()

            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()

            .authorizeHttpRequests()

            .requestMatchers("/api/v1/auth/login").permitAll()
            .requestMatchers("/api/v1/auth/refresh").permitAll()
            .requestMatchers(
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/actuator/**"
            ).permitAll()

            .anyRequest().authenticated()

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authenticationProvider(authenticationProvider())

            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}