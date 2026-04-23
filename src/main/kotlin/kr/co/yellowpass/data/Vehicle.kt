package kr.co.yellowpass.data

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.data.annotation.Id

@Entity
@Table(name = "vehicles")
data class Vehicle(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "vehicle_no")   // ⭐ 필수
    val vehicleNo: String,

    val username: String,
    val password: String,

    @ManyToOne
    @JoinColumn(name = "school_id")
    val school: School
)