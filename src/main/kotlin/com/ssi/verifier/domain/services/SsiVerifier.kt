package com.ssi.verifier.domain.services

import com.ssi.verifier.domain.models.ConnectionlessProofRequest
import com.ssi.verifier.domain.models.ProofRequestTemplate

interface SsiVerifier {
    fun allProofRequestTemplates(): List<ProofRequestTemplate>

    fun newConnectionlessProofRequest(proofRequestTemplateId: String): ConnectionlessProofRequest

}
