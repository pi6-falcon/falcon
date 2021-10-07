package com.falcon.falcon.security.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.Date
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtUtils(
    @Value("\${jwt.secret}")
    private var secret: String,
    @Value("\${jwt.expiration}")
    private var expiration: String
) {

    fun generateToken(username: String): String = Jwts.builder()
        .setSubject(username)
        .setExpiration(Date(System.currentTimeMillis() + expiration.toLong()))
        .signWith(SignatureAlgorithm.HS512, secret.toByteArray())
        .compact()

    fun isTokenValid(token: String): Boolean =
        getClaimsToken(token)?.let {
            val expirationDate = it.expiration
            it.subject != null && expirationDate.isValid()
        } ?: false

    fun getUsernameFromToken(token: String): String? =
        getClaimsToken(token)?.subject

    private fun getClaimsToken(token: String): Claims? {
        return runCatching {
            Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(token).body
        }.getOrNull()
    }
}

private fun Date.isValid(): Boolean {
    val now = Date(System.currentTimeMillis())
    return now.before(this)
}

