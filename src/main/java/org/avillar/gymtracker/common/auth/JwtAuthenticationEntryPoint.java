package org.avillar.gymtracker.common.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

  @Override
  public void commence(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final AuthenticationException authException)
      throws IOException {
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("error", true);
    responseMap.put("message", "Unauthorized");
    String responseMsg = new ObjectMapper().writeValueAsString(responseMap);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setHeader("content-type", "application/json");
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    response.getWriter().write(responseMsg);
  }
}
