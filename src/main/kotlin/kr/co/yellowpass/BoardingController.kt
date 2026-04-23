package kr.co.yellowpass

import kr.co.yellowpass.data.BoardingLog
import kr.co.yellowpass.data.BoardingRequest
import kr.co.yellowpass.data.Student
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api")
class BoardingController(
    private val studentRepository: StudentRepository,
    private val schoolRepository: SchoolRepository,
    private val boardingLogRepository: BoardingLogRepository   // ⭐ 추가
) {

    @PostMapping("/board")
    fun board(@RequestBody req: BoardingRequest): String {

        // 1️⃣ 학교 조회
        val school = schoolRepository.findById(req.schoolId)
            .orElseThrow { RuntimeException("학교 없음") }

        // 2️⃣ 학생 조회
        val student = studentRepository.findByQrCode(req.qrCode) ?: throw RuntimeException("등록되지 않은 학생")

        // ⭐ 핵심: boarding_log 저장
        val log = BoardingLog(
            student = student,
            boardedAt = LocalDateTime.now(),
            vehicleNo = "BUS-01"
        )

        boardingLogRepository.save(log)

        return "OK"
    }
}