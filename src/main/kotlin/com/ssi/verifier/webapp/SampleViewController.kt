package com.ssi.verifier.webapp

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@Api(description = "Sample Operations", tags = ["sample"])
@RequestMapping("/api/sampleview")
class SampleViewController {

    @ApiOperation(value = "Returns a simple string")
    @GetMapping("", produces = ["text/plain"])
    fun sample(): String {
        return "sample"
    }
}
