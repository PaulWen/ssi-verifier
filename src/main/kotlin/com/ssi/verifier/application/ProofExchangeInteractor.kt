package com.ssi.verifier.application

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
        return ssiVerifier.newConnectionlessProofRequest(proofTemplate)
    }

    fun verifiedProof(proofExchangeRecordDo: VerifiedProofExchange) {
        if (!proofExchangeRecordDo.isValid) {
            notificationService.invalidProofExchangeUpdate(proofExchangeRecordDo)
        } else {
            notificationService.validProofExchangeUpdate(proofExchangeRecordDo)
        }
    }
}
