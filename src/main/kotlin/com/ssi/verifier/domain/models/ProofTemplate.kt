package com.ssi.verifier.domain.models

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject


data class NewProofTemplate(
    val proofRequestTemplate: AnonCredsProofRequestTemplate
)

data class ProofTemplate(
    val id: ProofTemplateId,
    val proofRequestTemplate: AnonCredsProofRequestTemplate
)

data class ProofTemplateId(
    val value: String
)

class AnonCredsProofRequestTemplate(value: String) {
    val value: String

    init {
        val gson = GsonBuilder().serializeNulls().create()
        val input = gson.fromJson(value, JsonObject::class.java)

        val output = JsonObject()

        if (input.has("requested_attributes")) {
            output.add("requested_attributes", input.get("requested_attributes"))
        }

        if (input.has("requested_predicates")) {
            output.add("requested_predicates", input.get("requested_predicates"))
        }

        this.value = output.toString()
    }
}
