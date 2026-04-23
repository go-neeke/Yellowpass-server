package kr.co.yellowpass.data

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

    val vehicleNo: String,

    val username: String,
    val password: String,

    @ManyToOne
    @JoinColumn(name = "school_id")
    val school: School
)