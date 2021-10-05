package com.falcon.falcon.configuration.security

import com.falcon.falcon.core.usecase.user.FindByUserNameUseCase
import com.falcon.falcon.security.filter.JwtFilter
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
class SecurityConfig(
    private val findByUserNameUseCase: FindByUserNameUseCase,
    private val jwtFilter: JwtFilter
) : WebSecurityConfigurerAdapter() {

    override fun configure(authManagerBuilder: AuthenticationManagerBuilder) {
        authManagerBuilder.userDetailsService(findByUserNameUseCase)
    }

    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/auth").permitAll()
            .anyRequest().authenticated()

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}
