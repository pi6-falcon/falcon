package com.falcon.falcon.core.security

import com.falcon.falcon.core.entity.User
import com.falcon.falcon.core.usecase.user.FindByUserNameUseCase
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class JwtFilter(private val jwtUtils: JwtUtils, private val findByUserNameUseCase: FindByUserNameUseCase) :
    OncePerRequestFilter() {

    private val log = KotlinLogging.logger {}

    private val tempUserHeader = "tempUser"

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        request.getHeader(AUTHORIZATION)?.extractToken()?.let { token ->
            jwtUtils.getUsernameFromToken(token)?.let { username ->
                val user = findByUserNameUseCase.loadUserByUsername(username)

                SecurityContextHolder.getContext().authentication =
                    UsernamePasswordAuthenticationToken(user, null, user.authorities)
            }
        }

        request.getHeader(AUTHORIZATION).isNullOrBlank().let { isNull ->
            if (isNull && request.servletPath.isUrlPath() != null) {

                val tempUsername = request.getHeader(tempUserHeader)
                    ?: UUID.randomUUID().hashCode().toString() + "ttl"

                val contextUser = UserDetailsImpl(User(username = tempUsername, password = ""))

                response.addHeader(tempUserHeader, tempUsername)

                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
                    contextUser, null, contextUser.authorities
                )
            }
        }

        filterChain.doFilter(request, response)
    }
}

/**
 * Extract the JWT token from the header AUTHORIZATION.
 * Returns null if JWT is invalid or null/empty.
 */
private fun String.extractToken(): String? {
    if (!this.startsWith("Bearer")) {
        return null
    }
    // e.g Bearer 1234token
    return this.split(" ")[1]
}

private fun String.isUrlPath(): String? = this.takeIf { this == "/url" }



