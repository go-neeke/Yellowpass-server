package kr.co.yellowpass.server.repository

import kr.co.yellowpass.server.data.entity.BoardingLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface BoardingLogRepository : JpaRepository<BoardingLog, Long> {
    @Query("""
        SELECT bl
        FROM BoardingLog bl
        JOIN FETCH bl.student s
        JOIN FETCH s.school
        WHERE s.id IN :studentIds
        ORDER BY bl.boardedAt DESC
    """)
    fun findHistoryByStudentIds(
        @Param("studentIds") studentIds: List<Long>
    ): List<BoardingLog>

    @Query("""
    SELECT bl
    FROM BoardingLog bl
    JOIN FETCH bl.student s
    JOIN FETCH s.school
    WHERE bl.id = :boardingId
""")
    fun findDetailById(
        @Param("boardingId") boardingId: Long
    ): BoardingLog?

    @Query("""
    SELECT bl
    FROM BoardingLog bl
    JOIN FETCH bl.student s
    WHERE s.id IN :studentIds
    AND bl.boardedAt = (
        SELECT MAX(bl2.boardedAt)
        FROM BoardingLog bl2
        WHERE bl2.student.id = s.id
    )
""")
    fun findLatestByStudentIds(
        @Param("studentIds") studentIds: List<Long>
    ): List<BoardingLog>
}