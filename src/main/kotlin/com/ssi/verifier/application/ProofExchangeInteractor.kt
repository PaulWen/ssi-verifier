package com.ssi.verifier.application

import com.ssi.verifier.domain.models.ConnectionlessProofRequestDo
import com.ssi.verifier.domain.models.ProofExchangeRecordDo
import com.ssi.verifier.domain.models.ProofRequestTemplateDo
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

    fun newConnectionlessProofRequest(proofRequestTemplateId: String): ConnectionlessProofRequestDo {
        return ssiVerifier.newConnectionlessProofRequest(proofRequestTemplateId)
    }

    fun proofExchangeUpdate(proofExchangeRecordDo: ProofExchangeRecordDo) {
        notificationService.proofExchangeUpdate(proofExchangeRecordDo)
    }
}