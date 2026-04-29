package kr.co.yellowpass.server.data.request

data class SignupRequest(
    val schoolName: String,
    val username: String,
    val password: String
)