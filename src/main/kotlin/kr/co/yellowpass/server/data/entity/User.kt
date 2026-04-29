package kr.co.yellowpass.server.data.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import kr.co.yellowpass.server.data.Role

@Entity
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var username: String = "",

    var password: String = "",

    @Enumerated(EnumType.STRING)
    var role: Role = Role.PARENT
)