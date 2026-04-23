package kr.co.yellowpass

import kr.co.yellowpass.data.LoginRequest
import kr.co.yellowpass.data.Vehicle
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/vehicle")
class VehicleAuthController(
    private val vehicleRepository: VehicleRepository
) {

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): Vehicle {

        val vehicle = vehicleRepository.findByUsername(req.username)
            ?: throw RuntimeException("차량 없음")

        if (vehicle.password != req.password) {
            throw RuntimeException("비밀번호 틀림")
        }

        return vehicle
    }
}