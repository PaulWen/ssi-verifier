package com.ssi.verifier.domain.models

data class NewProofTemplate(
    val proofRequest: String
)

data class ProofTemplate(
    val id: ProofTemplateId,
    val proofRequest: String
)

data class ProofTemplateId(
    val value: String
)
