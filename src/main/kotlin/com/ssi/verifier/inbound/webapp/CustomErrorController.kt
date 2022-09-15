package com.ssi.verifier.inbound.webapp

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Controller
class CustomErrorController : AbstractAppController(), ErrorController {

    @RequestMapping("/error")
    fun handleError(request: HttpServletRequest, response: HttpServletResponse, model: Model): String {
        val statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)
        val url = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI)

        var errorMessage = ""
        when (statusCode) {
            404 -> errorMessage = "HTTP 404: The endpoint <code>$url</code> does not exist."
            405 -> errorMessage = "HTTP 405: The endpoint <code>$url</code> does not support the HTTP method."
        }

        model.addAttribute("errorMessage", errorMessage)

        return "error"
    }

}
