package com.ssi.verifier.domain.models

data class VerifiedProofExchange(
    val id: String,
    val isValid: Boolean,
    val presentation: Presentation? = null
)

data class Presentation(
    val credentialAttributes: Map<String, String>,
    val selfAttestedAttributes: Map<String, String>,
)

data class RequestedProofRevealedAttrGroup(
    val values: Map<String, String>
)
