package kr.co.schoolpass.server

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "students")
data class Student(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,
    val grade: Int,

    @Column(name = "class_no")
    val classNo: Int,

    @Column(name = "qr_code")
    val qrCode: String

    val boardedAt: LocalDateTime   // ✅ 추가
)