package com.falcon.falcon.security.filter

import com.falcon.falcon.constants.Header
import com.falcon.falcon.core.usecase.user.FindByUserNameUseCases
import com.falcon.falcon.security.utils.JwtUtils
import mu.KotlinLogging
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.http.HttpHeaders.AUTHORIZATION;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

@Component
class JwtFilter(
    private val jwtUtils: JwtUtils,
    private val findByUserNameUseCases: FindByUserNameUseCases): OncePerRequestFilter() {

    private val log = KotlinLogging.logger {}

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        log.info { "Jwt filter took the request..." }

        val header = request.getHeader(AUTHORIZATION)


        log.info { "Analyzing if exist a token" }

        if(header == null || verifyIfNotExistJwt(header)) {

            log.info { "Token not found" }

            filterChain.doFilter(request, response)
            return
        }

        log.info { "Token found" }

        val token = header.split(" ")[1].trim();

        log.info { "Analyzing if token is valid" }

        if (jwtUtils.isTokenValid(token)) {

            log.info { "Tokes is valid" }

            jwtUtils.getUserName(token)?.let {
                val user = findByUserNameUseCases.loadUserByUsername(it)

                SecurityContextHolder.getContext().authentication =
                    UsernamePasswordAuthenticationToken(user, null, user.authorities)
            }
        }

        log.info { "exit of the jwt filter" }

        filterChain.doFilter(request, response)

    }

    private fun verifyIfNotExistJwt(header: String): Boolean =
        StringUtils.isEmpty(header) || !header.startsWith(Header.BEARER.type)
}