package com.ssi.verifier.config

import com.ssi.verifier.inbound.webapp.interceptor.WebAppLoggerInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebAppConfig(
    private val webAppLoggerInterceptor: WebAppLoggerInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(webAppLoggerInterceptor).addPathPatterns("/app/**")
    }
}
