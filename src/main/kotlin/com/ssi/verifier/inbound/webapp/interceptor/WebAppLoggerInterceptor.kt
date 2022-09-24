package com.ssi.verifier.inbound.webapp.interceptor

import com.ssi.verifier.AppLogger
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class WebAppLoggerInterceptor(
    private val logger: AppLogger
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        return true // further process the request
    }

    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, exception: Exception?) {
        logger.webApp(
            httpMethod = request.method,
            uri = request.requestURI,
            httpCode = response.status
        )
    }
}
