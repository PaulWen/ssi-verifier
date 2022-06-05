package com.ssi.verifier.domain.services

import com.ssi.verifier.domain.models.VerifiedProofDo

interface NotificationService {
    fun validProofExchangeUpdate(verifiedProof: VerifiedProofDo)
    fun invalidProofExchangeUpdate(verifiedProof: VerifiedProofDo)
}
