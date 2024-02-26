package ga.injuk.security.configs.oauth

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class PrincipalOauth2UserService : DefaultOAuth2UserService() {

    // 구글 로그인시 구글로부터 전달받은 userRequest 데이터의 후처리를 위해 호출되는 함수
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        println("clientRegistration: ${userRequest?.clientRegistration}")
        println("accessToken: ${userRequest?.accessToken}") // 구글 로그인 후 코드를 받는 것이 아닌, 사용자 정보를 조회하기 위한 액세스 토큰까지 받아온다!
        println("registrationId: ${userRequest?.clientRegistration?.registrationId}")

        val result = super.loadUser(userRequest)
        println(result.attributes) // 그런데 사용자 정보도 이미 받아온다! 액세스 토큰과 사용자 정보를 함께 조회한 것임.
        return result
    }
}