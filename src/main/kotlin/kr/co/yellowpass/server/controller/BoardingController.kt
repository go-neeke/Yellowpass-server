package kr.co.yellowpass.server.controller

import kr.co.yellowpass.server.data.BoardingDetailResponse
import kr.co.yellowpass.server.data.BoardingHistoryResponse
import kr.co.yellowpass.server.data.BoardingLog
import kr.co.yellowpass.server.data.BoardingRequest
import kr.co.yellowpass.server.data.ChildResponse
import kr.co.yellowpass.server.repository.BoardingLogRepository
import kr.co.yellowpass.server.repository.DeviceTokenRepository
import kr.co.yellowpass.server.repository.ParentStudentRepository
import kr.co.yellowpass.server.repository.StudentRepository
import kr.co.yellowpass.server.repository.VehicleRepository
import kr.co.yellowpass.server.service.FcmService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

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
                boardedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul")),
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

    @GetMapping("/boarding/history")
    fun getBoardingHistory(
        @RequestParam parentId: Long
    ): ResponseEntity<Any> {

        return try {
            val mappings = parentStudentRepository.findWithStudentByParentId(parentId)

            if (mappings.isEmpty()) {
                return ResponseEntity.ok(emptyList<BoardingHistoryResponse>())
            }

            val studentIds = mappings.mapNotNull { it.student.id }

            if (studentIds.isEmpty()) {
                return ResponseEntity.ok(emptyList<BoardingHistoryResponse>())
            }

            val logs = boardingLogRepository.findHistoryByStudentIds(studentIds)

            val result = logs.map { log ->
                BoardingHistoryResponse(
                    boardingId = log.id ?: 0L,
                    studentId = log.student.id ?: 0L,
                    studentName = log.student.name,
                    schoolName = log.student.school.name,
                    grade = log.student.grade,
                    classNo = log.student.classNo,
                    boardedAt = log.boardedAt.toString(),
                    vehicleNo = log.vehicleNo
                )
            }

            ResponseEntity.ok(result)

        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(500).body(e.message ?: "서버 오류")
        }
    }

    @GetMapping("/boarding/{boardingId}")
    fun getBoardingDetail(
        @PathVariable boardingId: Long
    ): ResponseEntity<Any> {

        return try {
            val log = boardingLogRepository.findDetailById(boardingId)
                ?: return ResponseEntity.notFound().build()

            val result = BoardingDetailResponse(
                boardingId = log.id ?: 0L,
                studentId = log.student.id ?: 0L,
                studentName = log.student.name,
                schoolName = log.student.school.name,
                grade = log.student.grade,
                classNo = log.student.classNo,
                boardedAt = log.boardedAt.toString(),
                vehicleNo = log.vehicleNo
            )

            ResponseEntity.ok(result)

        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(500).body(e.message ?: "서버 오류")
        }
    }

    @GetMapping("/children")
    fun getChildren(
        @RequestParam parentId: Long
    ): ResponseEntity<List<ChildResponse>> {

        return try {

            // 1. 부모 → 자녀 조회
            val mappings = parentStudentRepository.findWithStudentByParentId(parentId)

            if (mappings.isEmpty()) {
                return ResponseEntity.ok(emptyList())
            }

            val students = mappings.map { it.student }
            val studentIds = students.mapNotNull { it.id }

            // 2. 최신 탑승 로그 조회
            val latestLogs = if (studentIds.isNotEmpty()) {
                boardingLogRepository.findLatestByStudentIds(studentIds)
            } else emptyList()

            val logMap = latestLogs.associateBy { it.student.id }

            val today = LocalDate.now(ZoneId.of("Asia/Seoul"))
            val now = LocalTime.now(ZoneId.of("Asia/Seoul"))

            // 3. 응답 생성
            val result = students.map { student ->

                val log = logMap[student.id]

                val isTodayBoarded = log?.boardedAt?.toLocalDate() == today

                val status = when {
                    isTodayBoarded -> "BOARDING"
                    now.isAfter(LocalTime.of(8, 30)) -> "DELAY"
                    else -> "NOT_BOARD"
                }

                ChildResponse(
                    studentId = student.id ?: 0L,
                    name = student.name,
                    schoolName = student.school.name,
                    grade = student.grade,
                    classNo = student.classNo,
                    lastBoardedAt = log?.boardedAt?.toString(),
                    status = status
                )
            }

            ResponseEntity.ok(result)

        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(500).body(emptyList())
        }
    }
}