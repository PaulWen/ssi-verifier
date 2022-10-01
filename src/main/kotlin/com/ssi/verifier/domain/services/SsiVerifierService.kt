package com.ssi.verifier.domain.services

import AnonCredsProofRequest
import com.ssi.verifier.domain.models.ConnectionlessProofRequest
import com.ssi.verifier.domain.models.ProofExchangeId

interface SsiVerifierService {

    fun newConnectionlessProofRequest(proofRequest: AnonCredsProofRequest): ConnectionlessProofRequest

    fun removeProofRequestData(proofExchangeId: ProofExchangeId)

}
