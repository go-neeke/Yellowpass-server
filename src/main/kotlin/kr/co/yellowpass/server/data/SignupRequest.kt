package kr.co.yellowpass.server.data

data class SignupRequest(
    val schoolName: String,
    val username: String,
    val password: String
)