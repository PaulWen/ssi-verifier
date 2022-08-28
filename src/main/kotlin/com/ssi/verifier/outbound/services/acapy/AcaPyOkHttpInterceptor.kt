package com.ssi.verifier.outbound.services.acapy

import okhttp3.Interceptor
import okhttp3.Response
import org.springframework.stereotype.Service

@Service
class AcaPyOkHttpInterceptor(
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val t1 = System.nanoTime()
        val response = chain.proceed(request)
        val t2 = System.nanoTime()

        val durationInMs = (t2 - t1) / 1e6

        println("type=http_request request=${request.method}${request.url.encodedPath} httpCode=${response.code} durationInMs=${durationInMs}")

        return response
    }
}
