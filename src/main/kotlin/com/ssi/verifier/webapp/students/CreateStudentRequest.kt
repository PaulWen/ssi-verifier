package com.ssi.verifier.webapp.students

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class CreateStudentRequest {
    @NotNull
    var studentId: Int? = null

    @NotEmpty
    var lastName: String? = null

    @NotEmpty
    var firstName: String? = null
}
