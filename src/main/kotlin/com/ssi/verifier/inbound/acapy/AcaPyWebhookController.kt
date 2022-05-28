package com.ssi.verifier.inbound.acapy

import com.ssi.verifier.application.ProofExchangeInteractor
import com.ssi.verifier.domain.models.ProofExchangeRecordDo
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

        override fun handleProof(proof: PresentationExchangeRecord) {
            if (proof.errorMsg != null) {
                // TODO error case
                return
            }

            proofExchangeInteractor.proofExchangeUpdate(
                ProofExchangeRecordDo(
                    proof.presentationExchangeId,
                    proof.state.toString(),
                    proof.state == PresentationExchangeState.VERIFIED,
                    proof.isVerified,
                )
            )
        }

    }
}
