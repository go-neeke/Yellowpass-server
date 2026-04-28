package kr.co.yellowpass.server.service

import kr.co.yellowpass.server.repository.ScanRepository
import org.springframework.stereotype.Service

@Service
class ScanService(
    private val repo: ScanRepository,
    private val fcmService: FcmService
) {

    fun scan(qrCode: String) {

        val targets = repo.findPushTargets(qrCode)

        if (targets.isEmpty()) {
            println("❌ 대상 없음")
            return
        }

        val studentName = targets.first().studentName

        val title = "탑승 알림"
        val body = "$studentName 학생이 버스에 탑승했습니다"

        // ✅ 1. 토큰 정리 (중복 제거 + null 방지)
        val tokens = targets
            .mapNotNull { it.token }
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .distinct()

        if (tokens.isEmpty()) {
            println("❌ 유효한 토큰 없음")
            return
        }

        // ✅ 2. 개별 전송 (안정성 ↑)
        tokens.forEach { token ->
            try {
                fcmService.sendMessage(token, title, body)
            } catch (e: Exception) {
                println("❌ FCM 실패: $token")
            }
        }

        println("✅ 전송 완료: ${tokens.size}건")
    }
}