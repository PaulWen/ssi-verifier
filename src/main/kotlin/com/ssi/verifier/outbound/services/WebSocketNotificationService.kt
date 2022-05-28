package com.ssi.verifier.outbound.services

import com.ssi.verifier.domain.models.ProofExchangeRecordDo
import com.ssi.verifier.domain.services.NotificationService
import org.springframework.stereotype.Service

@Service
class WebSocketNotificationService : NotificationService {
    override fun proofExchangeUpdate(proofExchangeRecordDo: ProofExchangeRecordDo) {
        println("Received Proof Exchange Update!")
    }
}
