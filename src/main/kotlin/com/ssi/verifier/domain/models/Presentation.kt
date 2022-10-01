package com.ssi.verifier.domain.models

data class Presentation(
    val credentialAttributes: Map<String, String>,
    val selfAttestedAttributes: Map<String, String>,
)
