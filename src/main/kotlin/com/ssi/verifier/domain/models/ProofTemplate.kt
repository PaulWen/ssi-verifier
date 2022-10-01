package com.ssi.verifier.domain.models

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject


data class ProofTemplate(
    val id: ProofTemplateId,
    val proofRequestTemplate: AnonCredsProofRequestTemplate
) {
    fun hasIdenticalProofRequest(otherProofRequestTemplate: AnonCredsProofRequestTemplate): Boolean {
        val gson = GsonBuilder().serializeNulls().create()
        val thisProofRequest = gson.fromJson(proofRequestTemplate.value, JsonObject::class.java)
        val otherProofRequest = gson.fromJson(otherProofRequestTemplate.value, JsonObject::class.java)

        return thisProofRequest.equals(otherProofRequest)
    }
}

