package kr.co.yellowpass.server.repository

import kr.co.yellowpass.server.data.ParentStudent
import org.springframework.data.jpa.repository.JpaRepository

interface ParentStudentRepository : JpaRepository<ParentStudent, Long> {
    fun findByStudentId(studentId: Long): List<ParentStudent>
}