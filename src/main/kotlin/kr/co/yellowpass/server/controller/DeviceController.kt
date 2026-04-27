package kr.co.yellowpass.server.controller

import kr.co.yellowpass.server.data.DeviceToken
import kr.co.yellowpass.server.data.DeviceTokenRequest
import kr.co.yellowpass.server.repository.DeviceTokenRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class DeviceController(
    private val deviceTokenRepository: DeviceTokenRepository
) {

    @PostMapping("/device-token")
    fun saveToken(@RequestBody request: DeviceTokenRequest): ResponseEntity<Any> {

        val entity = DeviceToken(
            parentId = request.parentId,
            fcmToken = request.fcmToken
        )

        deviceTokenRepository.save(entity)

        return ResponseEntity.ok().build()
    }
}