package com.ssi.verifier.outbound.repositories

import com.ssi.verifier.domain.models.NewProofTemplate
import com.ssi.verifier.domain.models.ProofTemplate
import com.ssi.verifier.domain.models.ProofTemplateId
import com.ssi.verifier.domain.repositories.ProofTemplateRepo
import com.ssi.verifier.outbound.repositories.models.ProofTemplateDao
import com.ssi.verifier.outbound.repositories.models.toDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

internal interface ProofTemplateJpaRepo : JpaRepository<ProofTemplateDao, String>

@Service
class ProofTemplateRepoJpa : ProofTemplateRepo {

    @Autowired
    private lateinit var proofTemplateJpaRepo: ProofTemplateJpaRepo

    override fun newProofTemplate(newProofTemplate: NewProofTemplate): ProofTemplateId {
        val proofTemplateDao = proofTemplateJpaRepo.save(newProofTemplate.toDao())
        return proofTemplateDao.toDomain().id
    }

    override fun updateProofTemplate(proofTemplate: ProofTemplate) {
        proofTemplateJpaRepo.save(proofTemplate.toDao())
    }

    override fun getProofTemplate(proofTemplateId: ProofTemplateId): ProofTemplate {
        val proofTemplateDao = proofTemplateJpaRepo.getById(proofTemplateId.value)
        return proofTemplateDao.toDomain()
    }
}
