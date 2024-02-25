package ga.injuk.security.models

import ga.injuk.security.common.Constants
import ga.injuk.security.common.PasswordEncoder
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import java.sql.Timestamp

@Entity(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    val username: String,
    val password: String,
    val email: String,
    @CreationTimestamp
    val createDate: Timestamp?,

    val role: String = Constants.ROLE_USER,
) {
    companion object {
        fun from(user: User): User = user.copy(
            password = PasswordEncoder.encode(user.password),
        )
    }
}