package com.ssi.verifier.config

import org.keycloak.KeycloakPrincipal
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.web.client.RestTemplate
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.time.Duration

@Configuration
class LissiAgentConfig(
    @Value("\${lissi-agent.url}") private val lissiAgentUrl: String?,
    @Value("\${lissi-agent.tenant-id}") private val lissiAgentTenantId: String?,
) {

    @Bean(name = ["LissiAgent"])
    @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    fun lissiAgentRestTemplate(): RestTemplate {
        val request = (RequestContextHolder
            .currentRequestAttributes() as ServletRequestAttributes).request
        val accessToken = (request.userPrincipal as KeycloakPrincipal<*>)
            .keycloakSecurityContext.tokenString

        val restTemplate = RestTemplateBuilder()
            .setConnectTimeout(Duration.ofSeconds(10))
            .rootUri(lissiAgentUrl!!)
            .defaultHeader("authorization", "Bearer $accessToken")
            .defaultHeader("x-tenant-id", lissiAgentTenantId)
            .build()

        return restTemplate
    }
}
