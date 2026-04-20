import kr.co.schoolpass.server.AdminRepository
import kr.co.schoolpass.server.SchoolRepository
import kr.co.schoolpass.server.data.Admin
import kr.co.schoolpass.server.data.LoginRequest
import kr.co.schoolpass.server.data.School
import kr.co.schoolpass.server.data.SignupRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val adminRepository: AdminRepository,
    private val schoolRepository: SchoolRepository
) {

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): Admin {
        val admin = adminRepository.findByUsername(req.username)
            ?: throw RuntimeException("사용자 없음")

        if (admin.password != req.password) {
            throw RuntimeException("비밀번호 틀림")
        }

        return admin
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