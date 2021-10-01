package com.falcon.falcon.configuration.security

import com.falcon.falcon.core.usecase.user.FindByUserNameUseCases
import com.falcon.falcon.security.filter.JwtFilter
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletResponse


@EnableWebSecurity
class SecurityConfig(
    private val findByUserNameUseCases: FindByUserNameUseCases,
    private val jwtFilter: JwtFilter
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(findByUserNameUseCases)
    }

    override fun configure(http: HttpSecurity) {

        http.cors().and().csrf().disable()

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

        http.exceptionHandling().authenticationEntryPoint { _, response, ex ->
            response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED, ex.message
            )
        }.and()

        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/auth").permitAll()
            .anyRequest().authenticated()

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}