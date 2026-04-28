package kr.co.yellowpass.server.repository

import kr.co.yellowpass.server.data.PushTarget
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class ScanRepository(
    private val jdbcTemplate: JdbcTemplate
) {

    fun findPushTargets(qrCode: String): List<PushTarget> {

        val sql = """
            SELECT s.name, dt.fcm_token
            FROM student s
            JOIN parent_student ps ON s.id = ps.student_id
            JOIN parents p ON ps.parent_id = p.id
            JOIN device_token dt ON p.id = dt.parent_id
            WHERE s.qr_code = ?
        """.trimIndent()

        return jdbcTemplate.query(sql, { rs, _ ->
            PushTarget(
                studentName = rs.getString("name"),
                token = rs.getString("fcm_token")
            )
        }, qrCode)
    }
}