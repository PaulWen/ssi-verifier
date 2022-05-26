package com.ssi.verifier.domain.models

data class ProofRequestTemplate(
    val id: String,
    val name: String,
    val dataUrlImage: String = ""
)
