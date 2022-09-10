package com.ssi.verifier.domain.services

import com.ssi.verifier.domain.models.VerifiedProofExchange

interface NotificationService {
    fun validProofExchangeUpdate(verifiedProofExchange: VerifiedProofExchange)
    fun invalidProofExchangeUpdate(verifiedProofExchange: VerifiedProofExchange)
}
