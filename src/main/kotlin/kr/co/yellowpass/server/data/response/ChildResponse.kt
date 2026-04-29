package kr.co.yellowpass.server.data.response

data class ChildResponse(
    val studentId: Long,
    val name: String,
    val schoolName: String,
    val grade: Int,
    val classNo: Int,
    val lastBoardedAt: String?,
    val status: String // BOARDING / NOT_BOARD
)