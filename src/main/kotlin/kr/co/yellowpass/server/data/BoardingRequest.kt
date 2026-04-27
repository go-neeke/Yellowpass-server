package kr.co.yellowpass.server.data

data class BoardingRequest(
    val qrCode: String,
    val schoolId: Long,
    val vehicleId: Long
)