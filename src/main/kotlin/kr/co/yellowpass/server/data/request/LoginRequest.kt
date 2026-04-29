package kr.co.yellowpass.server.data.request

import kr.co.yellowpass.server.data.Role

data class LoginRequest(
    val username: String,
    val password: String,
    val phone: String?,    // parent
    val role: Role
)