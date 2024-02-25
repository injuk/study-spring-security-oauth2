package ga.injuk.security.repositories

import ga.injuk.security.models.User
import org.springframework.data.jpa.repository.JpaRepository

// 기본적인 CRUD를 구현해줌
// JpaRepository를 확장하므로 @Repository 없이도 IoC가 처리됨!
interface UserRepository : JpaRepository<User, Int> {

    // JPA의 경우, 쿼리 메소드를 활용하여 findBy라는 prefix 이후에 select 문의 where 절의 조건을 명시할 수 있다.
    fun findByUsername(username: String): User?
}