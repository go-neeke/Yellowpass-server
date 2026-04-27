package kr.co.yellowpass.server.data

import jakarta.persistence.*

@Entity
data class School(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String
)