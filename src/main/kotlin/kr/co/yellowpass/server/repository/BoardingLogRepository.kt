package kr.co.yellowpass.server.repository

import kr.co.yellowpass.server.data.BoardingLog
import org.springframework.data.jpa.repository.JpaRepository

interface BoardingLogRepository : JpaRepository<BoardingLog, Long>