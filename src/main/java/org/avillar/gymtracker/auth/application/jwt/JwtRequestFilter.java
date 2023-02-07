package org.avillar.gymtracker.auth.application.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER_START = "Bearer ";

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtRequestFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        if (SecurityContextHolder.getContext().getAuthentication() == null && StringUtils.isNotEmpty(requestTokenHeader)) {
            if (!StringUtils.startsWith(requestTokenHeader, AUTHORIZATION_HEADER_START)) {
                log.warn("JWT Token does not begin with Bearer String");
            } else {
                this.verifyJwt(request, requestTokenHeader.substring(7));
            }
        }

        chain.doFilter(request, response);
    }

    private void verifyJwt(final HttpServletRequest request, final String jwtToken) {
        try {
            final String username = this.jwtTokenUtil.getUsernameFromToken(jwtToken);
            if (StringUtils.isEmpty(username)) {
                log.warn("No hay ningún nombre asociado al token JWT");
                return;
            }

            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (!Boolean.TRUE.equals(this.jwtTokenUtil.validateToken(jwtToken, userDetails))) {
                log.info("El token JWT no es válido");
                return;
            }

            final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } catch (IllegalArgumentException e) {
            log.error("Unable to fetch JWT Token");
        } catch (ExpiredJwtException e) {
            log.error("JWT Token is expired");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
