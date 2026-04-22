package kr.co.yellowpass.data

import jakarta.persistence.*

@Entity
data class Admin(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val username: String,
    val password: String,

    @ManyToOne
    @JoinColumn(name = "school_id")
    val school: School
)