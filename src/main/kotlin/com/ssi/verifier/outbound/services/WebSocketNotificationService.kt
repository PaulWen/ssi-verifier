package com.ssi.verifier.outbound.services

import com.ssi.verifier.domain.models.VerifiedProofExchange
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
    override fun validProofExchangeUpdate(verifiedProofExchange: VerifiedProofExchange) {
        val context = Context()
        context.setVariable("verifiedProofExchange", verifiedProofExchange)
        val htmlUpdate = templateEngine.process("proof-exchange/valid-proof-exchange-result", context)

        wsService.convertAndSend("/proof-exchange/${verifiedProofExchange.id}", htmlUpdate)
    }

    override fun invalidProofExchangeUpdate(verifiedProofExchange: VerifiedProofExchange) {
        val context = Context()
        val htmlUpdate = templateEngine.process("proof-exchange/invalid-proof-exchange-result", context)

        wsService.convertAndSend("/proof-exchange/${verifiedProofExchange.id}", htmlUpdate)
    }

}
