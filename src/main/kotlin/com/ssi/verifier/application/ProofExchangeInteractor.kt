package com.ssi.verifier.application

import AnonCredsProofRequest
import com.ssi.verifier.domain.models.ConnectionlessProofRequest
import com.ssi.verifier.domain.models.ProofTemplate
import com.ssi.verifier.domain.models.VerifiedProofExchange
import com.ssi.verifier.domain.services.NotificationService
import com.ssi.verifier.domain.services.SsiVerifierService
import org.springframework.stereotype.Service

@Service
class ProofExchangeInteractor(
    private val ssiVerifier: SsiVerifierService,
    private val notificationService: NotificationService
) {
    fun newConnectionlessProofRequest(proofTemplate: ProofTemplate): ConnectionlessProofRequest {
        return ssiVerifier.newConnectionlessProofRequest(AnonCredsProofRequest(proofTemplate))
    }

    fun verifiedProof(verifiedProofExchange: VerifiedProofExchange) {
        if (!verifiedProofExchange.isValid) {
            notificationService.invalidProofExchangeUpdate(verifiedProofExchange)
        } else {
            notificationService.validProofExchangeUpdate(verifiedProofExchange)
        }

        ssiVerifier.removeProofRequestData(verifiedProofExchange.id)
    }
}
