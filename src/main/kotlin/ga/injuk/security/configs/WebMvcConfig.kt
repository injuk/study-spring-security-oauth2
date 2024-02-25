package ga.injuk.security.configs

import org.springframework.boot.web.servlet.view.MustacheViewResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {

    override fun configureViewResolvers(registry: ViewResolverRegistry) {
        registry.viewResolver(
            MustacheViewResolver().apply {
                setCharset("UTF-8")
                setContentType("text/html; charset=UTF-8")
                setPrefix("classpath:/templates/")
                setSuffix(".html")
            }
        )
    }
}