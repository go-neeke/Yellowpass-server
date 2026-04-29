package kr.co.yellowpass.server.data.request

data class DeviceTokenRequest(
    val parentId: Long,
    val fcmToken: String
)