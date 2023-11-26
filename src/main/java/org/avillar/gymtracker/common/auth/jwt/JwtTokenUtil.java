package org.avillar.gymtracker.common.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.*;
import java.util.function.Function;
import javax.crypto.SecretKey;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Getter
@Slf4j
@Component
public class JwtTokenUtil {

  private static final SecretKey KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  @Value("${security.tokenType}")
  private String tokenType;

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
        Jwts.parser().verifyWith(getSignKey()).build().parseClaimsJws(token).getPayload();

    return (List<GrantedAuthority>) claims.get("authorities");
  }

  private <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
    return claimsResolver.apply(
        Jwts.parser().verifyWith(getSignKey()).build().parseClaimsJws(token).getPayload());
  }

  public boolean validateToken(final String token) {
    try {
      Jwts.parser().verifyWith(getSignKey()).build().parseClaimsJws(token);
      return getClaimFromToken(token, Claims::getExpiration)
          .after(new Date(System.currentTimeMillis()));
    } catch (Exception e) {
      log.info("The JWT Token is not valid", e);
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
        .claims(claims)
        .subject(userDetails.getUsername())
        .id(userDetails.getId().toString())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
        .signWith(getSignKey(), SIG.HS256)
        .compact();
  }

  private SecretKey getSignKey() {
    return StringUtils.isNotBlank(secret)
        ? Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
        : KEY;
  }
}
