package kr.co.yellowpass.server.data.request

data class SignupRequest(
    val username: String,
    val password: String,
    val schoolId: Long
)