package com.ssi.verifier.inbound.webapp

import com.ssi.verifier.application.ProofExchangeInteractor
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController(
    private val proofExchangeInteractor: ProofExchangeInteractor

) {

    @GetMapping("/")
    fun index(model: Model): String {
        val proofRequestTemplates = proofExchangeInteractor.allProofRequestTemplates()

        model.addAttribute("test", proofRequestTemplates.joinToString("; "))
        model.addAttribute("connectionlessProofRequest", proofExchangeInteractor.newConnectionlessProofRequest(proofRequestTemplates.get(1).id))

        return "index"
    }
}
