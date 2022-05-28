package com.ssi.verifier.outbound.services

import com.ssi.verifier.domain.models.ConnectionlessProofRequestDo
import com.ssi.verifier.domain.models.ProofRequestTemplateDo
import com.ssi.verifier.domain.services.SsiVerifierService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.client.postForObject
import java.util.*


@Service
class LissiAgentSsiVerifier(
    @Qualifier("LissiAgent") private val http: RestTemplate
) : SsiVerifierService {

    override fun allProofRequestTemplates(): List<ProofRequestTemplateDo> {
        val rateResponse: ResponseEntity<List<ProofRequestTemplateResponse>> = http.exchange("/proof-templates",
            HttpMethod.GET, null, object : ParameterizedTypeReference<List<ProofRequestTemplateResponse>>() {})

        return rateResponse.body!!.map { template -> template.toDo(this) }
    }

    override fun newConnectionlessProofRequest(proofRequestTemplateId: String): ConnectionlessProofRequestDo {
        val connectionlessProofRequestResponse = http.postForObject<ConnectionlessProofRequestResponse>(
            url = "/presentation-proof/connectionless?proofTemplateId={proofTemplateId}",
            uriVariables = mapOf("proofTemplateId" to proofRequestTemplateId)
        )

        return connectionlessProofRequestResponse.toDo()
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
    fun toDo(lissiAgentSsiVerifier: LissiAgentSsiVerifier): ProofRequestTemplateDo {
        if (imageUrl != null) {
            return ProofRequestTemplateDo(
                templateId,
                name,
                lissiAgentSsiVerifier.loadImageDataUrlEncoded(imageUrl)
            )
        }

        return ProofRequestTemplateDo(
            templateId,
            name
        )
    }
}

internal data class ConnectionlessProofRequestResponse(
    val exchangeId: String,
    val url: String
) {
    fun toDo(): ConnectionlessProofRequestDo {
        return ConnectionlessProofRequestDo(
            exchangeId,
            url
        )
    }
}


