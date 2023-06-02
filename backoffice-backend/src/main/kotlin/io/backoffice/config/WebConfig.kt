package io.backoffice.config

import io.backoffice.common.interceptor.RequestLoggingInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(requestLoggingInterceptor()!!)
    }

    @Bean
    fun requestLoggingInterceptor(): HandlerInterceptor? {
        return RequestLoggingInterceptor()
    }
}