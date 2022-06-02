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
        val htmlUpdate = """
            <turbo-stream action="append" target="proof-exchange-update">
                <template>
                    <div>
                        ID: ${proofExchangeRecordDo.id}
                        State: ${proofExchangeRecordDo.state}
                        Verified: ${proofExchangeRecordDo.isVerified}
                        Valid: ${proofExchangeRecordDo.isValid}
                    </div>
                </template>
            </turbo-stream>
        """

        wsService.convertAndSend("/proof", htmlUpdate)
    }
}
