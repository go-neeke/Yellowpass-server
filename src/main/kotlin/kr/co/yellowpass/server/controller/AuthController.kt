package kr.co.yellowpass.server.controller

import kr.co.yellowpass.server.data.entity.Admin
import kr.co.yellowpass.server.data.Role
import kr.co.yellowpass.server.data.request.LoginRequest
import kr.co.yellowpass.server.data.entity.School
import kr.co.yellowpass.server.data.request.SignupRequest
import kr.co.yellowpass.server.data.response.LoginResponse
import kr.co.yellowpass.server.repository.AdminRepository
import kr.co.yellowpass.server.repository.ParentRepository
import kr.co.yellowpass.server.repository.SchoolRepository
import kr.co.yellowpass.server.repository.VehicleRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val adminRepository: AdminRepository,
    private val parentRepository: ParentRepository,
    private val vehicleRepository: VehicleRepository,
    private val schoolRepository: SchoolRepository
) {

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): ResponseEntity<Any> {

        return when (req.role) {

            Role.ADMIN -> {
                val admin = adminRepository.findByUsername(req.username)
                    ?: return ResponseEntity.status(401).body("사용자 없음")

                if (admin.password != req.password) {
                    return ResponseEntity.status(401).body("비밀번호 틀림")
                }

                ResponseEntity.ok(
                    mapOf(
                        "userId" to admin.id,
                        "username" to admin.username,
                        "schoolId" to admin.school.id
                    )
                )
            }

            Role.VEHICLE -> {
                val vehicle = vehicleRepository.findByUsername(req.username)
                    ?: return ResponseEntity.status(401).body("사용자 없음")

                if (vehicle.password != req.password) {
                    return ResponseEntity.status(401).body("비밀번호 틀림")
                }

                ResponseEntity.ok(
                    LoginResponse(
                        userId = vehicle.id!!,
                        name = vehicle.username,
                        role = Role.VEHICLE
                    )
                )
            }

            Role.PARENT -> {
                val parent = parentRepository.findByPhoneNormalized(req.phone)
                    ?: return ResponseEntity.status(401).body("사용자 없음")

//                if (parent.password != req.password) {
//                    return ResponseEntity.status(401).body("비밀번호 틀림")
//                }

                ResponseEntity.ok(
                    LoginResponse(
                        userId = parent.id,
                        name = parent.name,
                        role = Role.PARENT
                    )
                )
            }
        }
    }

    @PostMapping("/signup")
    fun signup(@RequestBody req: SignupRequest): Admin {

        // 1. 학교 생성
        val school = schoolRepository.save(
            School(name = req.schoolName)
        )

        // 2. 관리자 생성
        val admin = adminRepository.save(
            Admin(
                username = req.username,
                password = req.password,
                school = school
            )
        )

        return admin
    }
}