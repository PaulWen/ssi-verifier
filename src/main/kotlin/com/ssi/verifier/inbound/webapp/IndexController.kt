package com.ssi.verifier.inbound.webapp

import com.ssi.verifier.domain.services.SsiVerifier
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController(
    private val ssiVerifier: SsiVerifier

) {

    @GetMapping("/")
    fun index(model: Model): String {
        val proofRequestTemplates = ssiVerifier.allProofRequestTemplates()

        model.addAttribute("test", proofRequestTemplates.joinToString("; "))
        model.addAttribute("connectionlessProofRequest", ssiVerifier.newConnectionlessProofRequest(proofRequestTemplates.first().id))

        return "index"
    }
}
