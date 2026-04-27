package kr.co.yellowpass.server.service

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import kr.co.yellowpass.server.repository.DeviceTokenRepository
import org.springframework.stereotype.Service

@Service
class FcmService(
    private val deviceTokenRepository: DeviceTokenRepository
) {

    fun sendPushToParent(parentId: Long, title: String, body: String) {

        val tokens = deviceTokenRepository.findByParentId(parentId)

        tokens.forEach { tokenEntity ->

            try {
                val message = Message.builder()
                    .setToken(tokenEntity.fcmToken)
                    .setNotification(
                        Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build()
                    )
                    // 🔥 중요: 앱에서 클릭 처리용 데이터
                    .putData("type", "RIDE_EVENT")
                    .build()

                FirebaseMessaging.getInstance().send(message)

            } catch (e: Exception) {
                e.printStackTrace()

                // ❗ 실패 토큰 삭제 (중요)
                deviceTokenRepository.delete(tokenEntity)
            }
        }
    }
}