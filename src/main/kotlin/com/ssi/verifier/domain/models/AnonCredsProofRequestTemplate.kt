package com.ssi.verifier.domain.models

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

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

        if (input.has("non_revoked")) {
            output.add("non_revoked", input.get("non_revoked"))
        }

        this.value = output.toString()
    }
}
