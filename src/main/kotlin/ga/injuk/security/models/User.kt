package ga.injuk.security.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import java.sql.Timestamp

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    val username: String?,
    val password: String?,
    val email: String?,
    val role: String?, // ROLE_USER, ROLE_ADMIN

    @CreationTimestamp
    val createDate: Timestamp?,
)