package com.ssi.verifier.infrastructure.services

import com.ssi.verifier.domain.models.ProofRequestTemplate
import com.ssi.verifier.domain.services.SsiVerifier
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.util.*


@Service
class LissiAgentSsiVerifier(
    @Qualifier("LissiAgent") private val http: RestTemplate
) : SsiVerifier {

    override fun allProofRequestTemplates(): List<ProofRequestTemplate> {
        val rateResponse: ResponseEntity<List<ProofRequestTemplateResponse>> = http.exchange("/proof-templates",
            HttpMethod.GET, null, object : ParameterizedTypeReference<List<ProofRequestTemplateResponse>>() {})

        return rateResponse.body!!.map { template -> template.toDo(this) }
    }

    override fun newConnectionlessProofRequest(proofRequestTemplateId: String): String {
        TODO("Not yet implemented")
    }

    internal fun loadImageDataUrlEncoded(imageUrl: String): String {
        val imageId = imageUrl.split("/").last()
        val imageBytes: ByteArray = http.getForObject("/images/download/$imageId", ByteArray::class)

        return "data:image/png;base64,${Base64.getEncoder().encodeToString(imageBytes)}"
    }
}

internal data class ProofRequestTemplateResponse(
    val templateId: String,
    val name: String,
    val imageUrl: String?
) {
    fun toDo(lissiAgentSsiVerifier: LissiAgentSsiVerifier): ProofRequestTemplate {
        if (imageUrl != null) {
            return ProofRequestTemplate(
                templateId,
                name,
                lissiAgentSsiVerifier.loadImageDataUrlEncoded(imageUrl)
            )
        }

        return ProofRequestTemplate(
            templateId,
            name
        )
    }
}

