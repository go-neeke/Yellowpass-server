package kr.co.yellowpass.server.service

import kr.co.yellowpass.server.repository.DeviceTokenRepository
import kr.co.yellowpass.server.repository.StudentRepository
import org.springframework.stereotype.Service

@Service
class BoardingService(
    private val studentRepository: StudentRepository,
    private val deviceTokenRepository: DeviceTokenRepository,
    private val fcmService: FcmService
) {

    fun handleScan(qrCode: String) {

        val student = studentRepository.findByQrCode(qrCode)
            ?: throw RuntimeException("학생 없음")

        val tokens = deviceTokenRepository.findAllByStudentId(student.id!!)

        tokens.forEach {
            fcmService.sendMessage(
                token = it.fcmToken,
                title = "탑승 알림",
                body = "${student.name} 학생이 버스에 탑승했습니다."
            )
        }
    }
}