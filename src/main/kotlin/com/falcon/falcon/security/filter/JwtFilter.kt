package com.falcon.falcon.security.filter

import com.falcon.falcon.core.usecase.user.FindByUserNameUseCase
import com.falcon.falcon.security.utils.JwtUtils
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(private val jwtUtils: JwtUtils, private val findByUserNameUseCase: FindByUserNameUseCase) : OncePerRequestFilter() {

    private val log = KotlinLogging.logger {}
    
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        request.getHeader(AUTHORIZATION)?.extractToken()?.let { token ->
            jwtUtils.getUsernameFromToken(token)?.let { username ->
                val user = findByUserNameUseCase.loadUserByUsername(username)

                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)
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
