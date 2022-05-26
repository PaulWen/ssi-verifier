package com.ssi.verifier.webapp

import com.ssi.verifier.domain.services.SsiVerifier
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@Api(description = "Sample Operations", tags = ["sample"])
@RequestMapping("/sampleview")
class SampleViewController(
    private val ssiVerifier: SsiVerifier
) {
    @ApiOperation(value = "Returns a simple string")
    @GetMapping("", produces = ["text/plain"])
    fun sample(model: Model): String {
        val proofRequestTemplates = ssiVerifier.allProofRequestTemplates()

        model.addAttribute("test", proofRequestTemplates.joinToString("; "))
        model.addAttribute("connectionlessProofRequest", ssiVerifier.newConnectionlessProofRequest(proofRequestTemplates.first().id))
        return "sample"
    }
}
