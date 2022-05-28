package com.ssi.verifier.domain.services

import com.ssi.verifier.domain.models.ProofExchangeRecordDo

interface NotificationService {
    fun proofExchangeUpdate(proofExchangeRecordDo: ProofExchangeRecordDo)
}
