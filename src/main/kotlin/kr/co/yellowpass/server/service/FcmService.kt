package kr.co.yellowpass.server.service

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import kr.co.yellowpass.server.repository.DeviceTokenRepository
import org.springframework.stereotype.Service

@Service
class FcmService {

    fun sendMessage(token: String, title: String, body: String) {

        val message = Message.builder()
            .setToken(token)
            .setNotification(
                Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build()
            )
            .build()

        FirebaseMessaging.getInstance().send(message)
    }
}