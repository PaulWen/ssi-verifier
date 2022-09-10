package com.ssi.verifier.inbound.webapp

import org.keycloak.KeycloakSecurityContext
import org.keycloak.adapters.RefreshableKeycloakSecurityContext
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest


@Controller
@RequestMapping(LogoutController.BASE_URL)
class LogoutController {
    companion object {
        const val BASE_URL = "/app/logout"
    }

    /**
     * Makes SSO Logout.
     * This endpoint has to be private. Otherwise, there will be no token to send logout to KeyCloak.
     *
     * @param request the request
     * @return redirect to index page
     * @throws ServletException if tomcat session logout throws exception
     */
    @GetMapping("")
    @Throws(ServletException::class)
    fun logout(request: HttpServletRequest): String {
        keycloakSessionLogout(request)
        tomcatSessionLogout(request)

        return "redirect:/"
    }

    private fun keycloakSessionLogout(request: HttpServletRequest) {
        val keycloakSecurityContext = getKeycloakSecurityContext(request)
        val keycloakDeployment = keycloakSecurityContext.deployment
        keycloakSecurityContext.logout(keycloakDeployment)
    }

    @Throws(ServletException::class)
    private fun tomcatSessionLogout(request: HttpServletRequest) {
        request.logout()
    }

    private fun getKeycloakSecurityContext(request: HttpServletRequest): RefreshableKeycloakSecurityContext {
        return request.getAttribute(KeycloakSecurityContext::class.java.name) as RefreshableKeycloakSecurityContext
    }
}
