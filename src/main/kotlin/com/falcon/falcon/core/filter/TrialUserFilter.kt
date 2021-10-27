package com.falcon.falcon.core.filter

import com.falcon.falcon.core.usecase.trial.CreateTrialToken
import com.falcon.falcon.core.usecase.trial.CreateTrialUser
import mu.KotlinLogging
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.Collections
import java.util.Enumeration
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper
import javax.servlet.http.HttpServletResponse

@Component
class TrialUserFilter(
    private val createTrialUser: CreateTrialUser,
    private val createTrialToken: CreateTrialToken,
) : OncePerRequestFilter() {

    private val log = KotlinLogging.logger {}
    private val temporaryUserHeader = "Temporary-User"
    private val temporaryUserHeaderAuth = "Temporary-User-Auth"
    val createUrlPath: RequestMatcher = AntPathRequestMatcher("/url", "POST")

    override fun shouldNotFilter(request: HttpServletRequest): Boolean = !createUrlPath.matches(request)

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val mutableRequest = MutableHttpServletRequest(request)
        request.getHeader(temporaryUserHeader)?.let { isTemporary ->
            if (isTemporary.toBoolean()) {
                val user = createTrialUser.execute()
                val token = createTrialToken.execute(user)

                mutableRequest.putHeader(AUTHORIZATION, "Bearer $token")
                response.addHeader(temporaryUserHeaderAuth, token)
            }
        }

        filterChain.doFilter(mutableRequest, response)
    }
}

internal class MutableHttpServletRequest(request: HttpServletRequest?) : HttpServletRequestWrapper(request) {

    private val customHeaders: MutableMap<String, String> = HashMap()

    fun putHeader(name: String, value: String) {
        customHeaders[name] = value
    }

    override fun getHeader(name: String): String? {
        val headerValue = customHeaders[name]
        return headerValue ?: (request as HttpServletRequest).getHeader(name)
    }

    override fun getHeaderNames(): Enumeration<String> {
        val set: MutableSet<String> = HashSet(customHeaders.keys)
        val e = (request as HttpServletRequest).headerNames
        while (e.hasMoreElements()) {
            val n = e.nextElement()
            set.add(n)
        }

        return Collections.enumeration(set)
    }
}
