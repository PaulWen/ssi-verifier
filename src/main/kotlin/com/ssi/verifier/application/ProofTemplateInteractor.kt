package com.ssi.verifier.application

import com.ssi.verifier.domain.models.NewProofTemplate
import com.ssi.verifier.domain.models.ProofTemplate
import com.ssi.verifier.domain.models.ProofTemplateId
import com.ssi.verifier.domain.repositories.ProofTemplateRepo
import org.springframework.stereotype.Service

@Service
class ProofTemplateInteractor(
    private val proofTemplateRepo: ProofTemplateRepo,
) {
    fun newProofTemplate(newProofTemplate: NewProofTemplate): ProofTemplateId {
        return proofTemplateRepo.newProofTemplate(newProofTemplate)
    }

    fun updateProofTemplate(proofTemplate: ProofTemplate) {
        proofTemplateRepo.updateProofTemplate(proofTemplate)
    }

    fun getProofTemplate(proofTemplateId: ProofTemplateId): ProofTemplate {
        val proofRequestJsonSample = """
            {
                "name": "Self-Attested",
                "version": "1.0",
                "requested_attributes": {
                  "self-attested-1": {
                    "name": "Name",
                    "restrictions": []
                  },
                  "self-attested-2": {
                    "name": "Age",
                    "restrictions": []
                  }
                },
                "requested_predicates": {},
                "nonce": "742995230032692452171111"
            }
        """

        return proofTemplateRepo.getProofTemplate(proofTemplateId)
    }


}
