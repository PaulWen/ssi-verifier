package com.ssi.verifier.inbound.acapy

import com.google.gson.Gson
import com.ssi.verifier.application.ProofExchangeInteractor
import com.ssi.verifier.domain.models.VerifiedProofDo
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.hyperledger.aries.api.present_proof.PresentationExchangeRecord
import org.hyperledger.aries.api.present_proof.PresentationExchangeState
import org.hyperledger.aries.webhook.EventHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("/api/acapy-webhook")
class AcaPyWebhookController(
    proofExchangeInteractor: ProofExchangeInteractor,
    @Value("\${lissi-agent.webhook-api-key}") private val webhookApiKey: String
) {
    private val acaPyEventHandler = AcaPyEventHandler(proofExchangeInteractor)
    private val log: Log = LogFactory.getLog(AcaPyWebhookController::class.java)


    private val X_API_KEY = "x-api-key"

    @ResponseBody
    @PostMapping("/topic/{topic}")
    fun ariesEvent(@PathVariable topic: String?, @RequestHeader headers: Map<String, String>, @RequestBody message: String?) {
        if (!authenticateAcaPy(headers)) {
            log.error("Failed to authenticate the AcaPy instance. Invalid '${X_API_KEY}' header.")
            throw IllegalStateException("Unauthenticated. Invalid '${X_API_KEY}' header.")
        }

        acaPyEventHandler.handleEvent(topic, message)
    }

    private fun authenticateAcaPy(headers: Map<String, String>): Boolean {
        if (webhookApiKey.isEmpty()) {
            return true
        }

        val apiKeyHeader: String? = headers.get(X_API_KEY)
        if (apiKeyHeader != null && apiKeyHeader.equals(webhookApiKey)) {
            return true
        }

        return false
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
