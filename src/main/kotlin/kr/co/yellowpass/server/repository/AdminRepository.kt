package kr.co.yellowpass.server.repository

import kr.co.yellowpass.server.data.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository : JpaRepository<Admin, Long> {

    fun findByUsername(username: String): Admin?
}