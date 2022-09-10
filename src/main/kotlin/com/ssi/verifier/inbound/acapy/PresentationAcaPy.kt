package com.ssi.verifier.inbound.acapy

import com.ssi.verifier.domain.models.Presentation
import com.ssi.verifier.domain.models.RequestedProofRevealedAttrGroup
import org.hyperledger.aries.api.present_proof.PresentProofRequest.ProofRequest.ProofRequestedPredicates

data class PresentationAcaPy(
    val revealed_attrs: Map<String, RequestedProofRevealedAttrAcaPy> = emptyMap(),
    val revealed_attr_groups: Map<String, RequestedProofRevealedAttrGroupAcaPy> = emptyMap(),
    val self_attested_attrs: Map<String, String> = emptyMap(),
    val unrevealed_attrs: Map<String, String> = emptyMap(),
) {
    fun toDo(proofRequestedPredicates: List<ProofRequestedPredicates>): Presentation {
        var credentialAttributes: Map<String, String> = emptyMap()

        credentialAttributes = credentialAttributes + revealed_attrs.mapValues { it.value.raw }
        revealed_attr_groups.values.forEach { attributeGroup ->
            credentialAttributes = credentialAttributes + attributeGroup.values.mapValues { it.value.raw }
        }
        proofRequestedPredicates.forEach { proofRequestedPredicate ->
            credentialAttributes = credentialAttributes + mapOf(proofRequestedPredicate.name to "${proofRequestedPredicate.pType} ${proofRequestedPredicate.pValue}")
        }

        return Presentation(
            credentialAttributes = credentialAttributes,
            selfAttestedAttributes = self_attested_attrs
        )
    }
}

data class RequestedProofRevealedAttrAcaPy(
    val raw: String,
    val encoded: String,
    val sub_proof_index: Int
)

data class RequestedProofRevealedAttrGroupAcaPy(
    val sub_proof_index: Int,
    val values: Map<String, AttributeAcaPy>
) {
    fun toDo() = RequestedProofRevealedAttrGroup(
        values = values.mapValues { it.value.raw }
    )
}


data class AttributeAcaPy(
    val raw: String,
    val encoded: String
)

