package com.ssi.verifier.domain.services

import com.ssi.verifier.domain.models.ProofRequestTemplate

interface SsiVerifier {
    fun allProofRequestTemplates(): List<ProofRequestTemplate>

    fun newConnectionlessProofRequest(proofRequestTemplateId: String): String

}
