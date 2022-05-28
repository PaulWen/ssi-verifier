package com.ssi.verifier.inbound.acapy

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@Api(description = "Sample Operations", tags = ["sample"])
@RequestMapping("/api/sample")
class SampleController {

    @ApiOperation(value = "Returns a simple string")
    @ResponseBody
    @GetMapping("", produces = ["text/plain"])
    fun sample(): String {
        return "Hello, world!"
    }
}
