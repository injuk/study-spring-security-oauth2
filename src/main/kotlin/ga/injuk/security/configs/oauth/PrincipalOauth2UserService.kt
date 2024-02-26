package ga.injuk.security.configs.oauth

import ga.injuk.security.common.PasswordEncoder
import ga.injuk.security.configs.auth.PrincipalDetails
import ga.injuk.security.models.User
import ga.injuk.security.repositories.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class PrincipalOauth2UserService(
    private val userRepository: UserRepository,
) : DefaultOAuth2UserService() {

    // 구글 로그인시 구글로부터 전달받은 userRequest 데이터의 후처리를 위해 호출되는 함수
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        println("clientRegistration: ${userRequest?.clientRegistration}")
        println("accessToken: ${userRequest?.accessToken}") // 구글 로그인 후 코드를 받는 것이 아닌, 사용자 정보를 조회하기 위한 액세스 토큰까지 받아온다!
        println("registrationId: ${userRequest?.clientRegistration?.registrationId}")

        val oAuth2User = super.loadUser(userRequest)
        println(oAuth2User.attributes) // 그런데 사용자 정보도 이미 받아온다! 액세스 토큰과 사용자 정보를 함께 조회한 것임.

        val provider = userRequest?.clientRegistration?.clientId as? String
        val providerId = oAuth2User.attributes["sub"] as? String
        val email = oAuth2User.attributes["email"] as? String
        val username = "$provider+$providerId" // username의 유일성을 보장하기 위함! google_00000....
        val password = PasswordEncoder.encode("안뇽") // oauth 기반 로그인에서는 필요 없으나, 그냥 만들어줌!
        val role = "ROLE_USER"

        val userEntity = userRepository.findByUsername(username)
        if(userEntity != null) {
            // 튕김
            println("이미 가입되어 있어요!")
        }

        // 저장
        val user = User(
            id = null,
            username = username,
            password = password,
            email = email!!,
            createDate = null,
            provider = provider!!,
            providerId = providerId!!,
            role = role,
        )
        userRepository.save(user)

        return PrincipalDetails(user = user, attributes = oAuth2User.attributes)
    }
}