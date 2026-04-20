package kr.co.schoolpass.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["kr.co.schoolpass"])
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}