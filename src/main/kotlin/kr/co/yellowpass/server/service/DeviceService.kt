package kr.co.yellowpass.server.service

import kr.co.yellowpass.server.data.DeviceToken
import kr.co.yellowpass.server.repository.DeviceTokenRepository
import org.springframework.stereotype.Service

@Service
class DeviceService(
    private val deviceTokenRepository: DeviceTokenRepository
) {

    fun saveOrUpdateToken(parentId: Long, token: String) {

        val existing = deviceTokenRepository.findByFcmToken(token)

        if (existing == null) {
            deviceTokenRepository.save(
                DeviceToken(
                    parentId = parentId,
                    fcmToken = token
                )
            )
        } else if (existing.parentId != parentId) {
            deviceTokenRepository.save(existing.copy(parentId = parentId))
        }
    }
}