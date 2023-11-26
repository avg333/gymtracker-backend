package org.avillar.gymtracker.authapi.login;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.instancio.Instancio;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@Execution(ExecutionMode.SAME_THREAD)
@Sql(scripts = "classpath:authapi/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class LoginIT {

  private static final String PATH = "/api/v1/signin";

  private static final String USER_ID = "e0e5af19-7c1e-4d1c-96a8-fedc1e1ccc65";
  private static final String USER_NAME_OK = "IT_TEST_USER_OK";
  private static final String USER_PASS_OK = "IT_TEST_PASS_OK";

  @Autowired private MockMvc mockMvc;

  @Value("${security.tokenType}")
  private String tokenType;

  private static JSONObject getJsonObject() throws JSONException {
    final JSONObject loginRequest = new JSONObject();
    loginRequest.put("username", USER_NAME_OK);
    loginRequest.put("password", USER_PASS_OK);
    return loginRequest;
  }

  @Test
  void shouldLoginSuccessfullyAndReturnLoginData() throws Exception {
    final JSONObject loginRequest = getJsonObject();

    mockMvc
        .perform(post(PATH).contentType(APPLICATION_JSON).content(loginRequest.toString()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(USER_ID))
        .andExpect(jsonPath("$.username").value(loginRequest.get("username")))
        .andExpect(jsonPath("$.type").value(tokenType))
        .andExpect(jsonPath("$.token").isString());
  }

  @Test
  void shouldNotLoginAndReturnForbiddenWhenCredentialsAreNotValid() throws Exception {
    final JSONObject loginRequest = getJsonObject();
    loginRequest.put("password", Instancio.create(String.class));

    mockMvc
        .perform(post(PATH).contentType(APPLICATION_JSON).content(loginRequest.toString()))
        .andDo(print())
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.message").value("Bad credentials"))
        .andExpect(jsonPath("$.validationErrors").isEmpty());
  }

  @Test
  void shouldNotLoginAndReturnBadRequestWhenRequestIsNotValid() throws Exception {
    final JSONObject loginRequest = getJsonObject();
    loginRequest.remove("username");

    mockMvc
        .perform(post(PATH).contentType(APPLICATION_JSON).content(loginRequest.toString()))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.message").value("Invalid request content."))
        .andExpect(jsonPath("$.validationErrors").isArray())
        .andExpect(jsonPath("$.validationErrors[0].field").value("username"))
        .andExpect(jsonPath("$.validationErrors[0].message").value("The username is required"));
  }
}
