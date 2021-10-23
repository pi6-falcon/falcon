package com.falcon.falcon.core.filter

import com.falcon.falcon.core.security.JwtUtils
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

@Component
class JwtFilter(private val jwtUtils: JwtUtils, private val findByUserNameUseCase: FindByUserNameUseCase) : OncePerRequestFilter() {

    private val log = KotlinLogging.logger {}

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        request.getHeader(AUTHORIZATION)?.extractToken()?.let { token ->
            jwtUtils.getUsernameFromToken(token)?.let { username ->
                val user = findByUserNameUseCase.loadUserByUsername(username)
                SecurityContextHolder.getContext().authentication =
                    UsernamePasswordAuthenticationToken(user, token, user.authorities)
            }
        }

        filterChain.doFilter(request, response)
    }
}

/**
 * Extension function responsible to extract the JWT token from the header AUTHORIZATION.
 *
 * @param String is the string itself "myString.extractToken()".
 * @return is the token already split, or null if none/invalid
 */
private fun String.extractToken(): String? {
    if (!this.startsWith("Bearer")) {
        return null
    }
    // e.g Bearer 1234token
    return this.split(" ")[1]
}

