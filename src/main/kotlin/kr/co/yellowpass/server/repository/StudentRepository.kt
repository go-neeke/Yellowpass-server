package kr.co.yellowpass.server.repository

import kr.co.yellowpass.server.data.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository : JpaRepository<Student, Long> {
    fun findBySchoolId(schoolId: Long): List<Student>
    fun findByQrCode(qrCode: String): Student?
}