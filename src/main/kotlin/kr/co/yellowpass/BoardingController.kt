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
    private val boardingLogRepository: BoardingLogRepository,
    private val vehicleRepository: VehicleRepository
) {

    @PostMapping("/board")
    fun board(@RequestBody req: BoardingRequest): String {

        val student = studentRepository.findByQrCode(req.qrCode)
            ?: throw RuntimeException("학생 없음")

        val vehicle = vehicleRepository.findById(req.vehicleId)
            .orElseThrow { RuntimeException("차량 없음") }

        val log = BoardingLog(
            student = student,
            boardedAt = LocalDateTime.now(),
            vehicleNo = vehicle.vehicleNo
        )

        boardingLogRepository.save(log)

        return "OK"
    }
}