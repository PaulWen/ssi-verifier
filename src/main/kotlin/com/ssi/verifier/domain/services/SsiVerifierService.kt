package com.ssi.verifier.domain.services

import com.ssi.verifier.domain.models.ConnectionlessProofRequestDo
import com.ssi.verifier.domain.models.ProofRequestTemplateDo

interface SsiVerifierService {
    fun allProofRequestTemplates(): List<ProofRequestTemplateDo>

    fun proofRequestTemplateById(id: String): ProofRequestTemplateDo

    fun newConnectionlessProofRequest(proofRequestTemplateId: String): ConnectionlessProofRequestDo

}
