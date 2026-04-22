package kr.co.yellowpass

import kr.co.yellowpass.data.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository : JpaRepository<Student, Long> {
    fun findBySchoolId(schoolId: Long): List<Student>
    fun findByQrCode(qrCode: String): Student?
}