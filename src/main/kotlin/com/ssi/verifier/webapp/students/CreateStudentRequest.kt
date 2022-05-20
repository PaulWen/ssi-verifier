package com.ssi.verifier.webapp.students

import com.ssi.verifier.domain.LastName
import org.hibernate.validator.constraints.Length
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class CreateStudentRequest {
    @NotNull
    var studentId: Int? = null

    @Valid
    var lastName: LastName? = null

    @NotEmpty
    @Length(min = 2, message = "Must be at least 2 characters long.")
    var firstName: String? = null
}
