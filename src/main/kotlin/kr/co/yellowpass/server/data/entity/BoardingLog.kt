package kr.co.yellowpass.server.data.entity

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

    @Column(name = "boarded_at")
    val boardedAt: LocalDateTime,

    @Column(name = "vehicle_no")
    val vehicleNo: String?
)