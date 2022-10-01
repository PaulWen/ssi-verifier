package com.ssi.verifier.inbound.acapy.models

data class RequestedProofRevealedAttrAcaPy(
    val raw: String,
    val encoded: String,
    val sub_proof_index: Int
)
