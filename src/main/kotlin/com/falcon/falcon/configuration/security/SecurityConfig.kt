package com.falcon.falcon.configuration.security

import com.falcon.falcon.core.filter.JwtFilter
import com.falcon.falcon.core.filter.TrialUserFilter
import com.falcon.falcon.core.usecase.user.FindByUserNameUseCase
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
class SecurityConfig(
    private val findByUserNameUseCase: FindByUserNameUseCase,
    private val jwtFilter: JwtFilter,
    private val trialUserFilter: TrialUserFilter,
) : WebSecurityConfigurerAdapter() {

    override fun configure(authManagerBuilder: AuthenticationManagerBuilder) {
        authManagerBuilder.userDetailsService(findByUserNameUseCase)
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/auth/**", "/api/user").permitAll()
            .antMatchers(HttpMethod.GET, "/*").permitAll()
            .antMatchers(HttpMethod.GET, "/api/ping").permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic()
            .and()
            .cors()

        http.addFilterBefore(trialUserFilter, UsernamePasswordAuthenticationFilter::class.java)
        http.addFilterAfter(jwtFilter, trialUserFilter::class.java)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
            .apply { allowedOrigins = listOf("*") }
            .apply { allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") }
            .apply { allowedHeaders = listOf("authorization", "content-type", "x-auth-token") }
            .apply { exposedHeaders = listOf("x-auth-token") }

        return UrlBasedCorsConfigurationSource()
            .apply { registerCorsConfiguration("/**", configuration) }
    }
}
