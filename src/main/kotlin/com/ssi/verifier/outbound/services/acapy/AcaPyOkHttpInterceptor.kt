package com.ssi.verifier.outbound.services.acapy

import com.ssi.verifier.AppLogger
import okhttp3.Interceptor
import okhttp3.Response
import org.springframework.stereotype.Service

@Service
class AcaPyOkHttpInterceptor(
    private val logger: AppLogger
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val t1 = System.nanoTime()
        val response = chain.proceed(request)
        val t2 = System.nanoTime()

        val durationInMs = (t2 - t1) / 1e6

        logger.acaPyApi(
            request.method,
            request.url.encodedPath,
            response.code,
            durationInMs
        )

        if (response.code != 200 && response.code != 201) {
            logger.acaPyApiError(
                httpMethod = request.method,
                uri = request.url.encodedPath,
                httpCode = response.code,
                durationInMs = durationInMs,
                message = response.body?.string() ?: ""
            )
        }

        return response
    }
}
