package kr.co.yellowpass

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["kr.co.yellowpass"])
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}