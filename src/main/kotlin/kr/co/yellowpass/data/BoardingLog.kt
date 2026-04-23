package kr.co.yellowpass.data

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "boarding_log")
data class BoardingLog(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "student_id")
    val student: Student,

    val boardedAt: LocalDateTime,

    val vehicleNo: String?
)