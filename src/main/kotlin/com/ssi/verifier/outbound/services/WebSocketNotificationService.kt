package com.ssi.verifier.outbound.services

import com.ssi.verifier.domain.models.VerifiedProofDo
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
    override fun validProofExchangeUpdate(verifiedProof: VerifiedProofDo) {
        val context = Context()
        context.setVariable("verifiedProof", verifiedProof)
        val htmlUpdate = templateEngine.process("valid-proof-exchange-update", context)

        wsService.convertAndSend("/proof/${verifiedProof.id}", htmlUpdate)
    }

    override fun invalidProofExchangeUpdate(verifiedProof: VerifiedProofDo) {
        val context = Context()

        val htmlUpdate = templateEngine.process("invalid-proof-exchange-update", context)
        wsService.convertAndSend("/proof/${verifiedProof.id}", htmlUpdate)
    }

}
