package kr.co.schoolpass.server

import kr.co.schoolpass.server.data.School
import org.springframework.data.jpa.repository.JpaRepository

interface SchoolRepository : JpaRepository<School, Long>