package com.ssi.verifier.inbound.webapp.controller

import com.ssi.verifier.AppLogger
import com.ssi.verifier.application.ProofExchangeInteractor
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.view.RedirectView

@Controller
class IndexController(
    private val proofExchangeInteractor: ProofExchangeInteractor,
    logger: AppLogger
) : AbstractAppController(logger) {

    @GetMapping("/")
    fun index(model: Model): RedirectView {
        return RedirectView(EditorController.BASE_URL)
    }

    @GetMapping("/app")
    fun appIndex(model: Model): RedirectView {
        return RedirectView(EditorController.BASE_URL)
    }
}
