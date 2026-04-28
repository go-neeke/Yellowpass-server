package kr.co.yellowpass.server.data

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var username: String = "",

    var password: String = "",

    @Enumerated(EnumType.STRING)
    var role: Role = Role.PARENT
)