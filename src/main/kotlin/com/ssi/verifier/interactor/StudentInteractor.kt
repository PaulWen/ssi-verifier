package com.ssi.verifier.interactor

import com.ssi.verifier.domain.LastName
import com.ssi.verifier.domain.Student
import org.springframework.stereotype.Service

@Service
class StudentInteractor {

    // This represents your database or any data source
    val studentList: MutableList<Student> = mutableListOf(
        Student(studentId = 1, lastName = LastName("Naruto"), firstName = "Uzumaki"),
        Student(studentId = 2, lastName = LastName("Sasuke"), firstName = "Uchiha"),
        Student(studentId = 3, lastName = LastName("Sakura"), firstName = "Haruno"),
        Student(studentId = 4, lastName = LastName("Kakashi"), firstName = "Hatake"),
        Student(studentId = 5, lastName = LastName("Neji"), firstName = "Hyuga"),
        Student(studentId = 6, lastName = LastName("Ino"), firstName = "Yamanaka"))

    fun getStudents(): List<Student> {
        return studentList
    }

    fun findStudentById(id: Int): Student? {
        return studentList.find { s -> s.studentId == id }
    }

    fun createStudent(student: Student) {
        studentList.add(student)
    }
}
