package com.ssi.verifier.outbound.services

import com.ssi.verifier.domain.models.ProofExchangeRecordDo
import com.ssi.verifier.domain.services.NotificationService
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class WebSocketNotificationService(
    private val wsService: SimpMessagingTemplate,
) : NotificationService {
    override fun proofExchangeUpdate(proofExchangeRecordDo: ProofExchangeRecordDo) {
        println("Received Proof Exchange Update!")
        wsService.convertAndSend("/proof", "${proofExchangeRecordDo.id}, ${proofExchangeRecordDo.state}, ${proofExchangeRecordDo.isValid}")
    }
}