package com.ssi.verifier.domain.services

import com.ssi.verifier.AppLogger
import com.ssi.verifier.domain.models.VerifiedProofExchange

abstract class NotificationService(
    private val logger: AppLogger
) {

    fun validProofExchangeUpdate(verifiedProofExchange: VerifiedProofExchange) {
        logger.notificationService("valid_proof_exchange_update", verifiedProofExchange.id)
        handleValidProofExchangeUpdate(verifiedProofExchange)
    }

    fun invalidProofExchangeUpdate(verifiedProofExchange: VerifiedProofExchange) {
        logger.notificationService("invalid_proof_exchange_update", verifiedProofExchange.id)
        handleInvalidProofExchangeUpdate(verifiedProofExchange)

    }

    protected abstract fun handleValidProofExchangeUpdate(verifiedProofExchange: VerifiedProofExchange)
    protected abstract fun handleInvalidProofExchangeUpdate(verifiedProofExchange: VerifiedProofExchange)
}
