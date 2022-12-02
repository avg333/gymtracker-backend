package org.avillar.gymtracker.auth.application.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER_START = "Bearer ";

    private final UserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtRequestFilter(UserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        if (SecurityContextHolder.getContext().getAuthentication() == null && StringUtils.isNotEmpty(requestTokenHeader)) {
            if (!StringUtils.startsWith(requestTokenHeader, AUTHORIZATION_HEADER_START)) {
                logger.warn("JWT Token does not begin with Bearer String");
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
                logger.info("El nombre de usuario del jwt esta vacio");
                return;
            }

            final UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
            if (!Boolean.TRUE.equals(this.jwtTokenUtil.validateToken(jwtToken, userDetails))) {
                logger.info("El jwt no es valido");
                return;
            }
            final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            logger.warn(SecurityContextHolder.getContext().getAuthentication());
        } catch (IllegalArgumentException e) {
            logger.error("Unable to fetch JWT Token");
        } catch (ExpiredJwtException e) {
            logger.error("JWT Token is expired");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
