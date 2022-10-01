package com.ssi.verifier.inbound.acapy.models

data class RequestedProofRevealedAttrGroupAcaPy(
    val sub_proof_index: Int,
    val values: Map<String, AttributeAcaPy>
) {
}
