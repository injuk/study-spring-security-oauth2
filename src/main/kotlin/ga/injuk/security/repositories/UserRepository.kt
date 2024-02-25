package ga.injuk.security.repositories

import ga.injuk.security.models.User
import org.springframework.data.jpa.repository.JpaRepository

// 기본적인 CRUD를 구현해줌
// JpaRepository를 확장하므로 @Repository 없이도 IoC가 처리됨!
interface UserRepository : JpaRepository<User, Int>