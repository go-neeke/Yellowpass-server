package kr.co.yellowpass

import kr.co.yellowpass.data.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository : JpaRepository<Admin, Long> {

    fun findByUsername(username: String): Admin?
}