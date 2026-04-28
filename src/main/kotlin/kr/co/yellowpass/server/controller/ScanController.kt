package kr.co.yellowpass.server.controller

import kr.co.yellowpass.server.service.ScanService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ScanController(
    private val scanService: ScanService
) {

    @PostMapping("/scan")
    fun scan(@RequestBody req: Map<String, String>) {
        val qrCode = req["qrCode"]!!
        scanService.scan(qrCode)
    }
}