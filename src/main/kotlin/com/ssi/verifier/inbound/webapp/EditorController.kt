package com.ssi.verifier.inbound.webapp

import com.ssi.verifier.application.ProofTemplateInteractor
import com.ssi.verifier.domain.models.NewProofTemplate
import io.swagger.annotations.ApiParam
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.view.RedirectView

@Controller
@RequestMapping(EditorController.BASE_URL)
class EditorController(
    private val proofTemplateInteractor: ProofTemplateInteractor

) {

    companion object {
        const val BASE_URL = "/app/editor"
    }

    @GetMapping("")
    fun index(model: Model): String {
        return "editor"
    }

    @PostMapping("save-proof-request-and-show-connectionless-proof-request")
    fun saveProofRequestAndShowConnectionlessProofRequest(
        @ApiParam(value = "AnonCreds Proof Request in JSON Format") @RequestParam(required = true, value = "proofRequestJson") proofRequestJson: String,
        model: Model
    ): RedirectView {
        val newProofTemplate = NewProofTemplate(proofRequestJson)
        val proofTemplateId = proofTemplateInteractor.newProofTemplate(newProofTemplate)

        return RedirectView("${ProofExchangeController.BASE_URL}/${proofTemplateId.value}")
    }
}
