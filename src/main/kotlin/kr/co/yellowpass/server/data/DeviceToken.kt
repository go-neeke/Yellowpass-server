package kr.co.yellowpass.server.data

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "device_token")
data class DeviceToken(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "parent_id")
    var parentId: Long,

    @Column(name = "fcm_token", length = 255, unique = true)
    var fcmToken: String
)