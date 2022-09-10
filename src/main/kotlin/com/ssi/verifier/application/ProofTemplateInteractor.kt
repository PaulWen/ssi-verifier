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
        return proofTemplateRepo.getProofTemplate(proofTemplateId)
    }


}
