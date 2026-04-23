package kr.co.yellowpass

import kr.co.yellowpass.data.BoardingLog
import org.springframework.data.jpa.repository.JpaRepository

interface BoardingLogRepository : JpaRepository<BoardingLog, Long>