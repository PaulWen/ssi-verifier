package com.ssi.verifier.outbound.services.acapy

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.ssi.verifier.domain.models.ConnectionlessProofRequestDo
import com.ssi.verifier.domain.models.ProofRequestTemplateDo
import com.ssi.verifier.domain.services.SsiVerifierService
import org.hyperledger.aries.AriesClient
import org.hyperledger.aries.api.present_proof.PresentProofRequest
import org.hyperledger.aries.api.present_proof.PresentProofRequest.ProofRequest
import org.hyperledger.aries.api.present_proof.PresentationExchangeRecord
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.*


@Service
class AcaPySsiVerifier(
    @Qualifier("AcaPy") private val acaPy: AriesClient,
) : SsiVerifierService {

    override fun allProofRequestTemplates(): List<ProofRequestTemplateDo> {
        return listOf(
            ProofRequestTemplateDo("001", "TestCredential", "")
        )
    }

    override fun proofRequestTemplateById(id: String): ProofRequestTemplateDo {
        if (id == "001") {
            return ProofRequestTemplateDo("001", "TestCredential", "")
        }

        throw NotImplementedError()
    }

    override fun newConnectionlessProofRequest(proofRequestTemplateId: String): ConnectionlessProofRequestDo {
        if (proofRequestTemplateId != "001") {
            throw NotImplementedError()
        }

        val proofRequestJson = """
            {
                "name": "Self-Attested",
                "version": "1.0",
                "requested_attributes": {
                  "self-attested-1": {
                    "name": "Name",
                    "restrictions": []
                  },
                  "self-attested-2": {
                    "name": "Age",
                    "restrictions": []
                  }
                },
                "requested_predicates": {},
                "nonce": "742995230032692452171111"
            }
        """

        val proofRequest = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create().fromJson(proofRequestJson, ProofRequest::class.java)

        val presentationExchange: PresentationExchangeRecord = acaPy.presentProofCreateRequest(PresentProofRequest.builder().proofRequest(proofRequest).build()).get()

        val did = acaPy.walletDid().get().first()
        val didCommEndpoint = acaPy.walletGetDidEndpoint(did.did).get().endpoint

        val didCommMessage = presentationExchange.presentationRequestDict

        val gson = GsonBuilder().serializeNulls().create()
        val serviceDecorator = gson.toJsonTree(
            ServiceDecorator(
                listOf(did.verkey),
                didCommEndpoint
            )
        )

        didCommMessage.add("~service", serviceDecorator)

        val didCommMessageEncoded =
            Base64.getUrlEncoder().withoutPadding().encodeToString(didCommMessage.toString().toByteArray())
        val encodedUrl = "didcomm://aries_connection_invitation?d_m=$didCommMessageEncoded"

        return ConnectionlessProofRequestDo(presentationExchange.presentationExchangeId, encodedUrl)
    }

    data class ServiceDecorator(
        val recipientKeys: List<String>,
        val serviceEndpoint: String,
        val routingKeys: String? = null
    )

}
