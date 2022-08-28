package com.ssi.verifier.outbound.services.acapy

import com.ssi.verifier.domain.models.ConnectionlessProofRequestDo
import com.ssi.verifier.domain.models.ProofRequestTemplateDo
import com.ssi.verifier.domain.services.SsiVerifierService
import org.hyperledger.aries.AriesClient
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service


@Service
class AcaPySsiVerifier(
    @Qualifier("AcaPy") private val acaPy: AriesClient
) : SsiVerifierService {

    override fun allProofRequestTemplates(): List<ProofRequestTemplateDo> {
        throw NotImplementedError()
    }

    override fun proofRequestTemplateById(id: String): ProofRequestTemplateDo {
        throw NotImplementedError()
    }

    override fun newConnectionlessProofRequest(proofRequestTemplateId: String): ConnectionlessProofRequestDo {
        throw NotImplementedError()
    }
}
