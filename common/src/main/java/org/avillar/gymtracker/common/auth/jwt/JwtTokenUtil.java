package org.avillar.gymtracker.common.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.io.Serializable;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil implements Serializable {

  private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  @Value("${security.secret}")
  private String secret;

  @Value("${security.jwtExpirationMs}")
  private long jwtExpirationMs;

  public UserDetailsImpl getUserDetailsImplFromToken(final String token) {
    return new UserDetailsImpl(
        getIdFromToken(token), getUsernameFromToken(token), null, getAuthoritiesFromToken(token));
  }

  private String getUsernameFromToken(final String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  private UUID getIdFromToken(final String token) {
    return UUID.fromString(getClaimFromToken(token, Claims::getId));
  }

  private List<GrantedAuthority> getAuthoritiesFromToken(final String token) {
    Claims claims =
        Jwts.parserBuilder()
            .setSigningKey(getSignKey())
            // .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();

    return (List<GrantedAuthority>) claims.get("authorities");
  }

  private <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
    return claimsResolver.apply(
        Jwts.parserBuilder()
            .setSigningKey(getSignKey())
            // .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody());
  }

  public boolean validateToken(final String token) {
    try {
      Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
      return true; // FIXME getClaimFromToken(token, Claims::getExpiration).before(new Date());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public String generateToken(final UserDetailsImpl userDetails) {
    final Map<String, Object> claims = new HashMap<>();
    claims.put("userId", userDetails.getId());
    claims.put("authorities", userDetails.getAuthorities());

    return createToken(claims, userDetails);
  }

  private String createToken(Map<String, Object> claims, final UserDetailsImpl userDetails) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userDetails.getUsername())
        .setId(userDetails.getId().toString())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs * 1000))
        .signWith(getSignKey(), SignatureAlgorithm.HS256) // .signWith(KEY)
        .compact();
  }

  private Key getSignKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
  }
}