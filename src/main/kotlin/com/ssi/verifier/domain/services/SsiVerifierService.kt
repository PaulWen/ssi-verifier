package com.ssi.verifier.domain.services

import com.ssi.verifier.domain.models.ConnectionlessProofRequest
import com.ssi.verifier.domain.models.ProofTemplate

interface SsiVerifierService {

    fun newConnectionlessProofRequest(proofTemplate: ProofTemplate): ConnectionlessProofRequest

}
