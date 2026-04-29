package kr.co.yellowpass.server.repository

import kr.co.yellowpass.server.data.entity.School
import org.springframework.data.jpa.repository.JpaRepository

interface SchoolRepository : JpaRepository<School, Long>