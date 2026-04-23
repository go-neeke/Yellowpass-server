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
class Vehicle(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "vehicle_no")
    var vehicleNo: String,

    var username: String,
    var password: String,

    @ManyToOne
    @JoinColumn(name = "school_id")
    var school: School
)