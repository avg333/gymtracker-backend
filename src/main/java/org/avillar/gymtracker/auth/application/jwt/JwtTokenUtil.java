package org.avillar.gymtracker.auth.application.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    @Value("${bezkoder.app.jwtExpirationMs}")
    private long jwtExpirationMs;



    public String getUsernameFromToken(final String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }


    public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(final UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs * 1000))
                .signWith(key)
                .compact();
    }

    public Boolean validateToken(final String token, final UserDetails userDetails) {
        return this.getUsernameFromToken(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Claims getAllClaimsFromToken(final String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(final String token) {
        return this.getExpirationDateFromToken(token).before(new Date());
    }

    private Date getExpirationDateFromToken(final String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

}
