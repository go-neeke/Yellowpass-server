package kr.co.yellowpass.server.controller

import kr.co.yellowpass.server.data.BoardingLog
import kr.co.yellowpass.server.data.BoardingRequest
import kr.co.yellowpass.server.repository.BoardingLogRepository
import kr.co.yellowpass.server.repository.DeviceTokenRepository
import kr.co.yellowpass.server.repository.ParentStudentRepository
import kr.co.yellowpass.server.repository.StudentRepository
import kr.co.yellowpass.server.repository.VehicleRepository
import kr.co.yellowpass.server.service.FcmService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api")
class BoardingController(
    private val studentRepository: StudentRepository,
    private val boardingLogRepository: BoardingLogRepository,
    private val vehicleRepository: VehicleRepository,
    private val parentStudentRepository: ParentStudentRepository,
    private val deviceTokenRepository: DeviceTokenRepository,
    private val fcmService: FcmService
) {

    @PostMapping("/board")
    fun board(@RequestBody req: BoardingRequest): ResponseEntity<Any> {

        return try {

            // ✅ 학생 조회
            val student = studentRepository.findByQrCode(req.qrCode)
                ?: return ResponseEntity.badRequest().body("학생 없음")

            val studentId = student.id
                ?: return ResponseEntity.badRequest().body("학생 ID 없음")

            // ✅ 차량 조회
            val vehicle = vehicleRepository.findById(req.vehicleId)
                .orElse(null)
                ?: return ResponseEntity.badRequest().body("차량 없음")

            // ✅ 1. 로그 저장
            val log = BoardingLog(
                student = student,
                boardedAt = LocalDateTime.now(),
                vehicleNo = vehicle.vehicleNo
            )

            boardingLogRepository.save(log)

            // ✅ 2. 보호자 매핑 조회
            val mappings = parentStudentRepository.findByStudentId(studentId)

            if (mappings.isEmpty()) {
                println("❌ 부모 연결 없음")
            }

            // ✅ 3. 토큰 조회 + 푸시
            mappings.forEach { mapping ->

                val tokens = deviceTokenRepository.findAllByParentId(mapping.parent.id)

                tokens.forEach { dt ->
                    val token = dt.fcmToken

                    if (token.isNullOrBlank()) return@forEach

                    try {
                        fcmService.sendMessage(
                            token,
                            "탑승 완료",
                            "${student.name} 학생이 ${vehicle.vehicleNo} 차량에 탑승했습니다."
                        )
                    } catch (e: Exception) {
                        println("❌ FCM 실패: ${e.message}")
                    }
                }
            }

            ResponseEntity.ok(
                mapOf(
                    "result" to "OK",
                    "studentName" to student.name
                )
            )

        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(500).body(e.message ?: "서버 오류")
        }
    }
}