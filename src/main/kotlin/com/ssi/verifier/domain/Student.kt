package com.ssi.verifier.domain

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotNull

class Student(
    val studentId: Int,
    val lastName: LastName,
    val firstName: String
)

class LastName {

    @NotNull
    @Length(min = 2, message = "Must be at least 2 characters long.")
    var name: String? = null

    constructor() {
    }

    constructor(name: String) {
        this.name = name
    }

}
