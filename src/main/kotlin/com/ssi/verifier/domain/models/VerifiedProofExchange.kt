package com.ssi.verifier.domain.models

data class VerifiedProofExchange(
    val id: ProofExchangeId,
    val isValid: Boolean,
    val presentation: Presentation? = null
)
