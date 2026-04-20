package kr.co.schoolpass.server.data

data class SignupRequest(
    val schoolName: String,
    val username: String,
    val password: String
)