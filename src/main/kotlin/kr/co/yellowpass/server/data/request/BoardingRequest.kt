package kr.co.yellowpass.server.data.request

data class BoardingRequest(
    val qrCode: String,
    val schoolId: Long,
    val vehicleId: Long
)