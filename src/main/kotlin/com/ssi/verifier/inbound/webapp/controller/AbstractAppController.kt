package com.ssi.verifier.inbound.webapp.controller

import com.ssi.verifier.AppLogger
import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import javax.servlet.http.HttpServletRequest


abstract class AbstractAppController(
    protected val logger: AppLogger
) {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    open fun handleError(request: HttpServletRequest, exception: Exception, model: Model): String {
        logger.webAppServerException(request.method, request.requestURI, 500, exception)

        model.addAttribute("errorMessage", exception.cause?.message)

        return "error"
    }
}
