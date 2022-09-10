package com.ssi.verifier.outbound.repositories.models

import com.ssi.verifier.domain.models.AnonCredsProofRequestTemplate
import com.ssi.verifier.domain.models.NewProofTemplate
import com.ssi.verifier.domain.models.ProofTemplate
import com.ssi.verifier.domain.models.ProofTemplateId
import org.hibernate.annotations.GenericGenerator
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "proof_template")
class ProofTemplateDao(
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    val id: String? = null,

    @Column(nullable = false, columnDefinition = "TEXT")
    val proofRequest: String,
) {

    fun toDomain(): ProofTemplate {
        return ProofTemplate(
            id = ProofTemplateId(id!!),
            proofRequestTemplate = AnonCredsProofRequestTemplate(proofRequest)
        )
    }
}

fun ProofTemplate.toDao(): ProofTemplateDao {
    return ProofTemplateDao(
        id = id.value,
        proofRequest = proofRequestTemplate.value
    )
}

fun NewProofTemplate.toDao(): ProofTemplateDao {
    return ProofTemplateDao(
        proofRequest = proofRequestTemplate.value
    )
}
