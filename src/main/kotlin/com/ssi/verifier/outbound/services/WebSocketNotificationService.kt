package com.ssi.verifier.outbound.services

import com.ssi.verifier.AppLogger
import com.ssi.verifier.domain.models.VerifiedProofExchange
import com.ssi.verifier.domain.services.NotificationService
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine

@Service
class WebSocketNotificationService(
    private val wsService: SimpMessagingTemplate,
    private val templateEngine: SpringTemplateEngine,
    logger: AppLogger
) : NotificationService(logger) {
    override fun handleValidProofExchangeUpdate(verifiedProofExchange: VerifiedProofExchange) {
        val context = Context()
        context.setVariable("verifiedProofExchange", verifiedProofExchange)
        val htmlUpdate = templateEngine.process("proof-exchange/valid-proof-exchange-result", context)

        wsService.convertAndSend("/proof-exchange/${verifiedProofExchange.id}", htmlUpdate)
    }

    override fun handleInvalidProofExchangeUpdate(verifiedProofExchange: VerifiedProofExchange) {
        val context = Context()
        val htmlUpdate = templateEngine.process("proof-exchange/invalid-proof-exchange-result", context)

        wsService.convertAndSend("/proof-exchange/${verifiedProofExchange.id}", htmlUpdate)
    }

}
