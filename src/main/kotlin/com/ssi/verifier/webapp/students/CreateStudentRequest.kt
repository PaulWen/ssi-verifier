package com.ssi.verifier.webapp.students

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class CreateStudentRequest {
    @NotNull
    var studentId: Int? = null

    @NotEmpty
    @Length(min = 2, message = "Must be at least 2 characters long.")
    var lastName: String? = null

    @NotEmpty
    @Length(min = 2, message = "Must be at least 2 characters long.")
    var firstName: String? = null
}
