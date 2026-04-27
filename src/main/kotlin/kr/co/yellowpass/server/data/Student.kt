package kr.co.yellowpass.server.data

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "students")
data class Student(

    @ManyToOne
    @JoinColumn(name = "school_id")
    val school: School,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val name: String,
    val grade: Int,

    @Column(name = "class_no")
    val classNo: Int,

    @Column(name = "qr_code", unique = true)
    val qrCode: String,
)