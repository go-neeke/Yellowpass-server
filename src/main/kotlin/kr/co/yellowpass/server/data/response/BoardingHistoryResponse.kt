package kr.co.yellowpass.server.data.response

data class BoardingHistoryResponse(
    val boardingId: Long,
    val studentId: Long,
    val studentName: String,
    val schoolName: String,
    val grade: Int,
    val classNo: Int,
    val boardedAt: String,
    val vehicleNo: String?
)