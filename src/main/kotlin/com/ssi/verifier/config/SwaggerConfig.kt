package com.ssi.verifier.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Tag
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket


/**
 * Build a Swagger2 configuration file
 */
@Configuration
class SwaggerConfig {

    private val apiPackage = "com.ssi.verifier"
    private val title = "API Documentation for the SSI Verifier"
    private val description = "This is an automatically generated API documentation."
    private val version = "v1.0"

    @Bean
    fun createRestApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .tags(Tag("sample", "Sample Endpoints"))
            .select()
            .apis(RequestHandlerSelectors.basePackage(apiPackage))
            .paths(PathSelectors.any())
            .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title(title)
            .description(description)
            .version(version)
            .build()
    }

}
