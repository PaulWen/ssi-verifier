package com.ssi.verifier.config

import com.ssi.verifier.outbound.services.acapy.AcaPyOkHttpInterceptor
import okhttp3.OkHttpClient
import org.hyperledger.aries.AriesClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.web.context.WebApplicationContext
import java.util.concurrent.TimeUnit

@Configuration
class AcaPyConfig(
    @Value("\${acapy.url}") private val acaPyUrl: String?,
    @Value("\${acapy.api-key}") private val acaPyApiKey: String?,
    @Value("\${acapy.http-timeout-in-seconds}") private val acaPyHttpTimeoutInSeconds: Long,
) {

    @Bean(name = ["AcaPy"])
    @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    fun buildAcaPyAriesClient(
        acaPyOkHttpInterceptor: AcaPyOkHttpInterceptor
    ): AriesClient {
        if (acaPyUrl == null) {
            throw Error("Unable to establish connection to AcaPy. AcaPy URL not configured.")
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(acaPyOkHttpInterceptor)
            .writeTimeout(acaPyHttpTimeoutInSeconds, TimeUnit.SECONDS)
            .readTimeout(acaPyHttpTimeoutInSeconds, TimeUnit.SECONDS)
            .connectTimeout(acaPyHttpTimeoutInSeconds, TimeUnit.SECONDS)
            .callTimeout(acaPyHttpTimeoutInSeconds, TimeUnit.SECONDS)
            .build()

        return AriesClient.builder()
            .url(acaPyUrl)
            .apiKey(acaPyApiKey)
            .client(okHttpClient)
            .build()
    }
}
