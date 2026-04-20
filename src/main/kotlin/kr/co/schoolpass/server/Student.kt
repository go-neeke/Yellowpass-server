package com.schoolpass.server

import jakarta.persistence.*

@Entity
@Table(name = "students")
data class Student(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,
    val grade: Int,
    val classNo: Int,

    @Column(name = "qr_code")
    val qrCode: String
)
