package com.ssi.verifier

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AppLogger {
    var logger: Logger = LoggerFactory.getLogger(AppLogger::class.java)

    fun springHandledException(uri: String, httpMethod: String, statusCode: Int, message: String) {
        logger.error("type=spring_handled_exception url=$uri httpMethod=$httpMethod statusCode=$statusCode message=\"$message\"")
    }

    fun webApp(
        httpMethod: String,
        uri: String,
        httpCode: Int
    ) {
        logger.info("type=web_app httpMethod=$httpMethod uri=$uri httpCode=${httpCode}")
    }

    fun webAppServerException(
        httpMethod: String,
        uri: String,
        httpCode: Int,
        exception: Exception
    ) {
        logger.error("type=web_app_server_exception httpMethod=$httpMethod uri=$uri httpCode=${httpCode} message=\"${exception.cause?.message}\" trace=\"${exception.stackTrace.filter { it.className.startsWith("com.ssi.verifier") }.toString()}\"")
    }

    fun acaPyApi(httpMethod: String, uri: String, httpCode: Int, durationInMs: Double) {
        logger.debug("type=acapy_api httpMethod=$httpMethod uri=$uri httpCode=$httpCode durationInMs=$durationInMs")
    }

    fun acaPyApiError(
        httpMethod: String,
        uri: String,
        httpCode: Int,
        durationInMs: Double,
        message: String
    ) {
        logger.error("type=acapy_api_error httpMethod=$httpMethod uri=$uri httpCode=${httpCode} durationInMs=${durationInMs} message=\"$message\"")
    }

    fun acaPyWebhook(topic: String, presentationExchangeId: String, connectionId: String, state: String, valid: Boolean) {
        logger.debug("type=acapy_webhook topic=$topic presentationExchangeId=$presentationExchangeId connectionId=$connectionId state=$state valid=$valid")
    }

    fun acaPyWebhookError(topic: String, message: String) {
        logger.error("type=acapy_webhook_error topic=$topic message=\"$message\"")
    }

    fun notificationSerivce(topic: String, message: String) {
        logger.info("type=notification_service topic=$topic message=$message")
    }
}
