package com.ssi.verifier.inbound.webapp

import com.ssi.verifier.application.ProofExchangeInteractor
import com.ssi.verifier.application.ProofTemplateInteractor
import com.ssi.verifier.domain.models.ProofTemplateId
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(ProofExchangeController.BASE_URL)
class ProofExchangeController(
    private val proofExchangeInteractor: ProofExchangeInteractor,
    private val proofTemplateInteractor: ProofTemplateInteractor
) : AbstractAppController() {
    companion object {
        const val BASE_URL = "/app/proof-exchange"
    }


    @GetMapping("{proofTemplateId}")
    fun index(
        @PathVariable proofTemplateId: String,
        model: Model
    ): String {
        val proofTemplate = proofTemplateInteractor.getProofTemplate(ProofTemplateId(proofTemplateId))

        val connectionlessProofRequest = proofExchangeInteractor.newConnectionlessProofRequest(proofTemplate)

        model.addAttribute("proofTemplateId", proofTemplateId)
        model.addAttribute("connectionlessProofRequest", connectionlessProofRequest)

        return "proof-exchange/connectionless-proof-request"
    }
}
