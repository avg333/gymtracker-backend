package org.avillar.gymtracker.auth.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(401);
        responseMap.put("error", true);
        responseMap.put("message", "Unauthorized");
        response.setHeader("content-type", "application/json");
        String responseMsg = mapper.writeValueAsString(responseMap);
        response.getWriter().write(responseMsg);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}