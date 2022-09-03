package com.ssi.verifier.outbound.services.acapy

import com.google.gson.GsonBuilder
import com.ssi.verifier.domain.models.ConnectionlessProofRequestDo
import com.ssi.verifier.domain.models.ProofRequestTemplateDo
import com.ssi.verifier.domain.services.SsiVerifierService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.hyperledger.aries.AriesClient
import org.hyperledger.aries.api.present_proof.PresentationExchangeRecord
import org.hyperledger.aries.config.GsonConfig
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*


@Service
class AcaPySsiVerifier(
    @Qualifier("AcaPyAriesClient") private val acaPyAriesClient: AriesClient,
    @Qualifier("AcaPyHttpClient") private val acaPyHttpClient: OkHttpClient,
    @Value("\${acapy.url}") private val acaPyUrl: String,
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

        val json = """
            {
              "proof_request": {
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
            }
        """

        val req: Request = Request.Builder()
            .url("$acaPyUrl/present-proof/create-request")
            .post(json.toRequestBody("application/json; charset=utf-8".toMediaType()))
            .build()
        val response: Response = acaPyHttpClient.newCall(req).execute()
        val presentationExchange = GsonConfig.defaultConfig().fromJson(response.body?.string(), PresentationExchangeRecord::class.java)

        val did = acaPyAriesClient.walletDid().get().first()
        val didCommEndpoint = acaPyAriesClient.walletGetDidEndpoint(did.did).get().endpoint

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
