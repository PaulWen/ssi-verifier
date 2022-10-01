import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.ssi.verifier.domain.models.ProofTemplate
import java.math.BigInteger
import java.security.DrbgParameters
import java.security.SecureRandom
import java.time.Instant

class AnonCredsProofRequest(proofTemplate: ProofTemplate) {
    val value: String

    init {
        val gson = GsonBuilder().serializeNulls().create()

        val proofRequestJson = gson.fromJson(proofTemplate.proofRequestTemplate.value, JsonObject::class.java)
        proofRequestJson.addProperty("name", "Proof Request")
        proofRequestJson.addProperty("version", "1.0")
        proofRequestJson.addProperty("nonce", generateNonce())

        val proofRequestWithTimePlaceholders = proofRequestJson.toString()
        val proofRequest = replaceTimePlaceholdersForRevocationChecks(proofRequestWithTimePlaceholders)

        this.value = proofRequest
    }

    private fun replaceTimePlaceholdersForRevocationChecks(proofRequest: String): String {
        val nowInEpochMilliseconds = Instant.now().toEpochMilli().toString()
        val nowInEpochSeconds = nowInEpochMilliseconds.substring(0, 10)

        return proofRequest.replace(oldValue = "\$now", newValue = nowInEpochSeconds, ignoreCase = false)
    }

    private fun generateNonce(): String {
        val nonce = ByteArray(80 / 8)

        val securityAlgorithm = "DRBG"
        val securityStrength = 128

        val securityAlgorithmCapability = DrbgParameters.instantiation(
            securityStrength,
            DrbgParameters.Capability.RESEED_ONLY,
            null
        )

        SecureRandom.getInstance(
            securityAlgorithm,
            securityAlgorithmCapability
        ).nextBytes(nonce)

        return BigInteger(1, nonce).toString()
    }
}
