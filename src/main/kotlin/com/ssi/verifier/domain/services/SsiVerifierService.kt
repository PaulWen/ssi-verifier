package com.ssi.verifier.domain.services

import AnonCredsProofRequest
import com.ssi.verifier.domain.models.ConnectionlessProofRequest

interface SsiVerifierService {

    fun newConnectionlessProofRequest(proofReuqest: AnonCredsProofRequest): ConnectionlessProofRequest

}
