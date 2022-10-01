package com.ssi.verifier.inbound.webapp.controller

import com.ssi.verifier.AppLogger
import com.ssi.verifier.application.ProofTemplateInteractor
import com.ssi.verifier.domain.models.AnonCredsProofRequestTemplate
import com.ssi.verifier.domain.models.NewProofTemplate
import com.ssi.verifier.domain.models.ProofTemplateId
import io.swagger.annotations.ApiParam
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.view.RedirectView

@Controller
@RequestMapping(EditorController.BASE_URL)
class EditorController(
    private val proofTemplateInteractor: ProofTemplateInteractor,
    logger: AppLogger
) : AbstractAppController(logger) {

    companion object {
        const val BASE_URL = "/app/editor"
    }

    @GetMapping("")
    fun index(model: Model): String {
        val sampleProofRequestJson = """
            {
                "requested_attributes": {
                  "Name": {
                    "name": "Name",
                    "restrictions": []
                  },
                  "Age": {
                    "name": "Age",
                    "restrictions": []
                  }
                },
                "requested_predicates": {},
                "non_revoked": {}
            }
        """

        model.addAttribute("saveAction", "$BASE_URL/save-proof-request-and-show-connectionless-proof-request")
        model.addAttribute("proofRequestJson", sampleProofRequestJson)
        return "editor"
    }

    @GetMapping("{proofTemplateId}")
    fun editExistingProofTemplate(
        @PathVariable proofTemplateId: String,
        model: Model
    ): String {
        val proofTemplate = proofTemplateInteractor.getProofTemplate(ProofTemplateId(proofTemplateId))

        model.addAttribute("saveAction", "$BASE_URL/$proofTemplateId/update-proof-request-and-show-connectionless-proof-request")
        model.addAttribute("proofRequestJson", proofTemplate.proofRequestTemplate.value)

        return "editor"
    }

    @PostMapping("save-proof-request-and-show-connectionless-proof-request")
    fun saveProofRequestAndShowConnectionlessProofRequest(
        @ApiParam(value = "AnonCreds Proof Request in JSON Format") @RequestParam(required = true, value = "proofRequestJson") proofRequestJson: String,
        model: Model
    ): RedirectView {
        val newProofTemplate = NewProofTemplate(AnonCredsProofRequestTemplate(proofRequestJson))
        val proofTemplateId = proofTemplateInteractor.newProofTemplate(newProofTemplate)

        return RedirectView("${ProofExchangeController.BASE_URL}/${proofTemplateId.value}")
    }

    @PostMapping("{proofTemplateId}/update-proof-request-and-show-connectionless-proof-request")
    fun updateProofRequestAndShowConnectionlessProofRequest(
        @PathVariable proofTemplateId: String,
        @ApiParam(value = "AnonCreds Proof Request in JSON Format") @RequestParam(required = true, value = "proofRequestJson") proofRequestJson: String,
        model: Model
    ): RedirectView {
        val newProofRequest = AnonCredsProofRequestTemplate(proofRequestJson)
        val existingProofTemplate = proofTemplateInteractor.getProofTemplate(ProofTemplateId(proofTemplateId))

        if (existingProofTemplate.hasIdenticalProofRequest(newProofRequest)) {
            return RedirectView("${ProofExchangeController.BASE_URL}/${proofTemplateId}")
        }

        val newProofTemplate = NewProofTemplate(newProofRequest)
        val newProofTemplateId = proofTemplateInteractor.newProofTemplate(newProofTemplate)
        return RedirectView("${ProofExchangeController.BASE_URL}/${newProofTemplateId.value}")
    }
}
