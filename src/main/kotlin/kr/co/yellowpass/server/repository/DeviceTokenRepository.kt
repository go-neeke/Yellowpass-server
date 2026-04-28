package kr.co.yellowpass.server.repository

import kr.co.yellowpass.server.data.DeviceToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface DeviceTokenRepository : JpaRepository<DeviceToken, Long> {

    fun findByFcmToken(fcmToken: String): DeviceToken?

    fun findAllByParentId(parentId: Long): List<DeviceToken>

    @Query("""
        SELECT dt FROM DeviceToken dt
        WHERE dt.parentId IN (
            SELECT ps.parent.id FROM ParentStudent ps
            WHERE ps.student.id = :studentId
        )
    """)
    fun findAllByStudentId(@Param("studentId") studentId: Long): List<DeviceToken>
}