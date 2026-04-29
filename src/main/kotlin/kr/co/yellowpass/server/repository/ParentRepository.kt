package kr.co.yellowpass.server.repository

import kr.co.yellowpass.server.data.entity.Parent
import org.springframework.data.jpa.repository.JpaRepository

interface ParentRepository : JpaRepository<Parent, Long> {
    fun findByPhone(phone: String?): Parent?
}