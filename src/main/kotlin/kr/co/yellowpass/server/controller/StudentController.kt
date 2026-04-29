package kr.co.yellowpass.server.controller

import kr.co.yellowpass.server.data.entity.Student
import kr.co.yellowpass.server.repository.StudentRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/students")
class StudentController(
    private val repository: StudentRepository
) {

    @PostMapping
    fun create(@RequestBody req: Student): Student {
        return repository.save(req)
    }

    @GetMapping
    fun getStudents(@RequestParam schoolId: Long): List<Student> {
        return repository.findBySchoolId(schoolId)
    }
}