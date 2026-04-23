package kr.co.yellowpass

import kr.co.yellowpass.data.Vehicle
import org.springframework.data.jpa.repository.JpaRepository

interface VehicleRepository : JpaRepository<Vehicle, Long> {
    fun findByUsername(username: String): Vehicle?
}