package com.ssi.verifier.domain.repositories

import com.ssi.verifier.domain.models.NewProofTemplate
import com.ssi.verifier.domain.models.ProofTemplate
import com.ssi.verifier.domain.models.ProofTemplateId

interface ProofTemplateRepo {
    fun newProofTemplate(newProofTemplate: NewProofTemplate): ProofTemplateId

    fun updateProofTemplate(proofTemplate: ProofTemplate)

    fun getProofTemplate(proofTemplateId: ProofTemplateId): ProofTemplate
}
