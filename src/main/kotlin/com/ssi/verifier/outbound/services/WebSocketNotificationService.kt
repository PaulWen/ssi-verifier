package com.ssi.verifier.outbound.services

import com.ssi.verifier.domain.models.ProofExchangeRecordDo
import com.ssi.verifier.domain.services.NotificationService
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine

@Service
class WebSocketNotificationService(
    private val wsService: SimpMessagingTemplate,
    private val templateEngine: SpringTemplateEngine
) : NotificationService {
    override fun proofExchangeUpdate(proofExchangeRecord: ProofExchangeRecordDo) {
        val context = Context()
        context.setVariable("proofExchangeRecord", proofExchangeRecord)
        val htmlUpdate = templateEngine.process("proof-exchange-update", context)

        wsService.convertAndSend("/proof/${proofExchangeRecord.id}", htmlUpdate)
    }
}
