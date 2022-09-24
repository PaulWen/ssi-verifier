package com.ssi.verifier.inbound.webapp.controller

import com.ssi.verifier.AppLogger
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Controller
class CustomErrorController(
    logger: AppLogger
) : AbstractAppController(logger), ErrorController {

    @RequestMapping("/error")
    fun handleError(request: HttpServletRequest, response: HttpServletResponse, model: Model): String {
        val uri: String = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI) as String
        val statusCode: Int = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) as Int
        val httpMethod: String = request.method
        val defaultErrorMessage: String = request.getAttribute(RequestDispatcher.ERROR_MESSAGE) as String

        var errorMessage = ""
        when (statusCode) {
            404 -> errorMessage = "HTTP 404: The endpoint <code>$uri</code> does not exist."
            405 -> errorMessage = "HTTP 405: The endpoint <code>$uri</code> does not support the HTTP method <code>$httpMethod</code>."
            else -> errorMessage = "HTTP $statusCode: $defaultErrorMessage"
        }

        logger.springHandledException(uri, httpMethod, statusCode, errorMessage)

        model.addAttribute("errorMessage", errorMessage)

        return "error"
    }
}
