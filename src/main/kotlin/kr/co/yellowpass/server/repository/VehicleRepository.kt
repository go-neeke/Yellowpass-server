package kr.co.yellowpass.server.repository

import kr.co.yellowpass.server.data.Vehicle
import org.springframework.data.jpa.repository.JpaRepository

interface VehicleRepository : JpaRepository<Vehicle, Long> {
    fun findByUsername(username: String): Vehicle?
}