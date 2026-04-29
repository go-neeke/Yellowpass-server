package kr.co.yellowpass.server.data.response

import kr.co.yellowpass.server.data.Role

data class LoginResponse(
    val userId: Long,
    val name: String,
    val role: Role
)