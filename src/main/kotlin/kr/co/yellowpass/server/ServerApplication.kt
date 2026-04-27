package kr.co.yellowpass.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["kr.co.yellowpass.server"])
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}