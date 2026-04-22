package kr.co.yellowpass

import kr.co.yellowpass.data.School
import org.springframework.data.jpa.repository.JpaRepository

interface SchoolRepository : JpaRepository<School, Long>