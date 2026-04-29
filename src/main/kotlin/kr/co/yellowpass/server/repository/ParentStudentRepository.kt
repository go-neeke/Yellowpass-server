package kr.co.yellowpass.server.repository

import kr.co.yellowpass.server.data.entity.ParentStudent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ParentStudentRepository : JpaRepository<ParentStudent, Long> {
    fun findByStudentId(studentId: Long): List<ParentStudent>

    fun findByParent_Id(parentId: Long): List<ParentStudent>

    @Query("""
        SELECT ps
        FROM ParentStudent ps
        JOIN FETCH ps.student s
        JOIN FETCH s.school
        WHERE ps.parent.id = :parentId
    """)
    fun findWithStudentByParentId(
        @Param("parentId") parentId: Long
    ): List<ParentStudent>

}