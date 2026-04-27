package kr.co.yellowpass.server.controller

import kr.co.yellowpass.server.data.BoardingLog
import kr.co.yellowpass.server.data.BoardingRequest
import kr.co.yellowpass.server.repository.BoardingLogRepository
import kr.co.yellowpass.server.repository.ParentStudentRepository
import kr.co.yellowpass.server.repository.StudentRepository
import kr.co.yellowpass.server.repository.VehicleRepository
import kr.co.yellowpass.server.service.FcmService
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime


@RestController
@RequestMapping("/api")
class BoardingController(
    private val studentRepository: StudentRepository,
    private val boardingLogRepository: BoardingLogRepository,
    private val vehicleRepository: VehicleRepository,
    private val parentStudentRepository: ParentStudentRepository,
    private val fcmService: FcmService
) {

    @PostMapping("/board")
    fun board(@RequestBody req: BoardingRequest): Map<String, Any> {

        val student = studentRepository.findByQrCode(req.qrCode)
            ?: throw RuntimeException("학생 없음")

        val vehicle = vehicleRepository.findById(req.vehicleId)
            .orElseThrow { RuntimeException("차량 없음") }

        // ✅ 1. 로그 저장 (무조건 성공해야 함)
        val log = BoardingLog(
            student = student,
            boardedAt = LocalDateTime.now(),
            vehicleNo = vehicle.vehicleNo
        )

        boardingLogRepository.save(log)

        // ✅ 2. 푸시 전송 (실패해도 OK)
        val mappings = parentStudentRepository.findByStudentId(student.id)

        mappings.forEach {
            try {
                fcmService.sendPushToParent(
                    it.parentId,
                    "탑승 완료",
                    "${student.name} 학생이 ${vehicle.vehicleNo} 차량에 탑승했습니다."
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return mapOf(
            "result" to "OK",
            "studentName" to student.name
        )
    }
}