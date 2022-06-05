package com.ssi.verifier.inbound.webapp

import com.ssi.verifier.application.ProofExchangeInteractor
import io.swagger.annotations.ApiParam
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class ProofRequestController(
    private val proofExchangeInteractor: ProofExchangeInteractor

) {

    @GetMapping("/proof-request/{id}")
    fun index(
        @ApiParam(value = "Proof Request Template ID") @PathVariable(required = true, value = "id") id: String,
        model: Model
    ): String {
        val proofRequestTemplate = proofExchangeInteractor.proofRequestTemplateById(id)
        val connectionlessProofRequest = proofExchangeInteractor.newConnectionlessProofRequest(proofRequestTemplate.id)

        model.addAttribute("proofRequestTemplateName", proofRequestTemplate.name)
        model.addAttribute("connectionlessProofRequest", connectionlessProofRequest)

        return "connectionless-proof-request"
    }
}
