package com.ssi.verifier.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Description
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.spring5.view.ThymeleafViewResolver
import org.thymeleaf.templateresolver.ServletContextTemplateResolver
import javax.servlet.ServletContext


class ThymeleafConfig {

    @Bean
    @Description("Thymeleaf Template Resolver")
    fun templateResolver(servletContext: ServletContext): ServletContextTemplateResolver {
        val templateResolver = ServletContextTemplateResolver(servletContext)
        templateResolver.suffix = ".html"
        templateResolver.setTemplateMode("HTML5")
        return templateResolver
    }

    @Bean
    @Description("Thymeleaf Template Engine")
    fun templateEngine(templateResolver: ServletContextTemplateResolver): SpringTemplateEngine {
        val templateEngine = SpringTemplateEngine()
        templateEngine.setTemplateResolver(templateResolver)
        return templateEngine
    }

    @Bean
    @Description("Thymeleaf View Resolver")
    fun viewResolver(templateEingine: SpringTemplateEngine): ThymeleafViewResolver? {
        val viewResolver = ThymeleafViewResolver()
        viewResolver.templateEngine = templateEingine
        viewResolver.order = 1
        return viewResolver
    }
}
