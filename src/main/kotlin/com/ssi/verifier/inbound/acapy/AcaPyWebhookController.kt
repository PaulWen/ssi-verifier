package com.ssi.verifier.inbound.acapy

import com.google.gson.Gson
import com.ssi.verifier.application.ProofExchangeInteractor
import com.ssi.verifier.domain.models.VerifiedProofDo
import org.hyperledger.aries.api.present_proof.PresentationExchangeRecord
import org.hyperledger.aries.api.present_proof.PresentationExchangeState
import org.hyperledger.aries.webhook.EventHandler
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("/api/acapy-webhook")
class AcaPyWebhookController(
    proofExchangeInteractor: ProofExchangeInteractor
) {
    private val acaPyEventHandler = AcaPyEventHandler(proofExchangeInteractor)


    @ResponseBody
    @PostMapping("/topic/{topic}")
    fun ariesEvent(@PathVariable topic: String?, @RequestBody message: String?) {
        acaPyEventHandler.handleEvent(topic, message)
    }

    private class AcaPyEventHandler(
        private val proofExchangeInteractor: ProofExchangeInteractor,
    ) : EventHandler() {
        var gson = Gson()

        override fun handleProof(proof: PresentationExchangeRecord) {
            if (proof.errorMsg != null) {
                proofExchangeInteractor.verifiedProof(
                    VerifiedProofDo(
                        proof.presentationExchangeId,
                        false
                    )
                )
                return
            }

            if (proof.roleIsVerifierAndVerified()) {
                proofExchangeInteractor.verifiedProof(
                    VerifiedProofDo(
                        proof.presentationExchangeId,
                        proof.state == PresentationExchangeState.VERIFIED,
                        gson.fromJson(proof.presentation.get("requested_proof"), PresentationAcaPy::class.java).toDo(proof.presentationRequest.requestedPredicates.values.toList())
                    )
                )
            }
        }

    }
}
