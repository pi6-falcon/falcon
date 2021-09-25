package com.falcon.falcon.configuration.security

import com.falcon.falcon.constants.AppRoutes
import com.falcon.falcon.core.usecase.user.FindByUserNameUseCases
import com.falcon.falcon.security.filter.JwtFilter
import com.falcon.falcon.security.utils.JwtUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletResponse


@EnableWebSecurity
class SecurityConfig(
    private val findByUserNameUseCases: FindByUserNameUseCases,
    private val jwtFilter: JwtFilter) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(findByUserNameUseCases)
    }

    override fun configure(http: HttpSecurity) {
        http.let {

            // Disable cors
            it.cors().and().csrf().disable()

            // Set session type
            it.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

            // Set exception handler to auth
            it.exceptionHandling().authenticationEntryPoint {
                    _, response, ex -> response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED, ex.message)
            }.and()

            // Set routes
            it.authorizeRequests()
                .antMatchers(AppRoutes.AUTH.httpMethod[0] , AppRoutes.AUTH.path).permitAll()
                .anyRequest().authenticated()

            // Set jwt token filter
            it.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        }
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

}