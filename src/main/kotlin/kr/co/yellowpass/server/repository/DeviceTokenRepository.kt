package kr.co.yellowpass.server.repository

import kr.co.yellowpass.server.data.DeviceToken
import org.springframework.data.jpa.repository.JpaRepository

interface DeviceTokenRepository : JpaRepository<DeviceToken, Long> {

    fun findByParentId(parentId: Long): List<DeviceToken>

    fun findByFcmToken(fcmToken: String): DeviceToken?
}