package com.ssi.verifier.inbound.webapp

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ExceptionHandler


abstract class AbstractAppController {
    @ExceptionHandler(Exception::class)
    open fun handleProductNotFoundError(exception: Exception, model: Model): String {
        model.addAttribute("errorMessage", exception.cause?.localizedMessage)

        return "error"
    }
}
