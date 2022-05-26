package com.ssi.verifier.infrastructure.services

import com.ssi.verifier.domain.models.ProofRequestTemplate
import com.ssi.verifier.domain.services.SsiVerifier
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class LissiAgentSsiVerifier(
    @Qualifier("LissiAgent") private val http: RestTemplate
) : SsiVerifier {

    override fun allProofRequestTemplates(): List<ProofRequestTemplate> {
        val rateResponse: ResponseEntity<List<ProofRequestTemplateResponse>> = http.exchange("/proof-templates",
            HttpMethod.GET, null, object : ParameterizedTypeReference<List<ProofRequestTemplateResponse>>() {})

        return rateResponse.body!!.map { template -> template.toDo() }
    }

    override fun newConnectionlessProofRequest(proofRequestTemplateId: String): String {
        TODO("Not yet implemented")
    }
}

data class ProofRequestTemplateResponse(
    val templateId: String,
    val name: String,
    val imageUrl: String?
) {
    fun toDo(): ProofRequestTemplate {
        if (imageUrl != null) {
            return ProofRequestTemplate(
                templateId,
                name,
                imageUrl
            )
        }

        return ProofRequestTemplate(
            templateId,
            name
        )
    }
}

