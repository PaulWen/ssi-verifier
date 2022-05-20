package com.ssi.verifier.webapp.students

import com.ssi.verifier.domain.Student
import com.ssi.verifier.interactor.StudentInteractor
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid

@Controller
@RequestMapping("/students")
class StudentController(
    private val studentInteractor: StudentInteractor
) {

    @GetMapping("")
    fun listStudents(model: Model): String {
        model.addAttribute("students", studentInteractor.getStudents())
        return "students/students"
    }

    @GetMapping("/{studentId}")
    fun findStudent(@PathVariable("studentId") studentId: Int, model: Model): String {
        model.addAttribute("student", studentInteractor.findStudentById(studentId))
        return "students/student"
    }

    @GetMapping("/create")
    fun createStudentPage(model: Model): String {
        model.addAttribute("createStudentRequest", CreateStudentRequest())
        return "students/new-student-form"
    }

    @PostMapping("/create")
    fun addStudent(@Valid createStudentRequest: CreateStudentRequest, result: BindingResult): String {

        if (result.hasErrors()) {
            return "students/new-student-form";
        }

        studentInteractor.createStudent(Student(
            studentId = createStudentRequest.studentId!!,
            lastName = createStudentRequest.lastName!!,
            firstName = createStudentRequest.firstName!!))

        return "redirect:" + createStudentRequest.studentId
    }

}
