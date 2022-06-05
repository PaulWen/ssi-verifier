package com.ssi.verifier.domain.models

data class VerifiedProofDo(
    val id: String,
    val isValid: Boolean,
    val presentation: PresentationDo? = null
)

data class PresentationDo(
    val credentialAttributes: Map<String, String>,
    val selfAttestedAttributes: Map<String, String>,
)

data class RequestedProofRevealedAttrGroupDo(
    val values: Map<String, String>
)
