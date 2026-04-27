package kr.co.yellowpass.server.data

data class DeviceTokenRequest(
    val parentId: Long,
    val fcmToken: String
)