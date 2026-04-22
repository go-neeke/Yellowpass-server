package kr.co.yellowpass

import kr.co.yellowpass.data.BoardingRequest
import kr.co.yellowpass.data.Student
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api")
class BoardingController(
    private val studentRepository: StudentRepository,
    private val schoolRepository: SchoolRepository   // ⭐ 추가
) {

    @PostMapping("/board")
    fun board(@RequestBody req: BoardingRequest): String {

        // 1️⃣ 학교 조회
        val school = schoolRepository.findById(req.schoolId)
            .orElseThrow { RuntimeException("학교 없음") }

        // 2️⃣ 학생 조회
        var student = studentRepository.findByQrCode(req.qrCode)

        if (student == null) {
            // 3️⃣ 없으면 생성
            student = studentRepository.save(
                Student(
                    school = school,   // ⭐ 여기 핵심
                    name = "신규학생",
                    grade = 0,
                    classNo = 0,
                    qrCode = req.qrCode,
                    boardedAt = LocalDateTime.now()
                )
            )
        } else {
            // 4️⃣ 있으면 업데이트
            student.boardedAt = LocalDateTime.now()
            studentRepository.save(student)
        }

        return "OK"
    }
}