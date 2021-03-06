package com.ssi.verifier.application

import com.ssi.verifier.domain.models.ConnectionlessProofRequestDo
import com.ssi.verifier.domain.models.ProofRequestTemplateDo
import com.ssi.verifier.domain.models.VerifiedProofDo
import com.ssi.verifier.domain.services.NotificationService
import com.ssi.verifier.domain.services.SsiVerifierService
import org.springframework.stereotype.Service

@Service
class ProofExchangeInteractor(
    private val ssiVerifier: SsiVerifierService,
    private val notificationService: NotificationService
) {

    fun allProofRequestTemplates(): List<ProofRequestTemplateDo> {
        return ssiVerifier.allProofRequestTemplates()
    }

    fun proofRequestTemplateById(id: String): ProofRequestTemplateDo {
        return ssiVerifier.proofRequestTemplateById(id)
    }

    fun newConnectionlessProofRequest(proofRequestTemplateId: String): ConnectionlessProofRequestDo {
        return ssiVerifier.newConnectionlessProofRequest(proofRequestTemplateId)
    }

    fun verifiedProof(proofExchangeRecordDo: VerifiedProofDo) {
        if (!proofExchangeRecordDo.isValid) {
            notificationService.invalidProofExchangeUpdate(proofExchangeRecordDo)
        } else {
            notificationService.validProofExchangeUpdate(proofExchangeRecordDo)
        }
    }
}
