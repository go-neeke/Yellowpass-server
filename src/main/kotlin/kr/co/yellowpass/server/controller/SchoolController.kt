package kr.co.yellowpass.server.controller

import kr.co.yellowpass.server.data.entity.School
import kr.co.yellowpass.server.repository.SchoolRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class SchoolController(
    private val schoolRepository: SchoolRepository
) {

    @GetMapping("/schools")
    fun getSchools(): List<School> {
        return schoolRepository.findAll()
    }
}