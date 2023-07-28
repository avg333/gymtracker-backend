package org.avillar.gymtracker.authapi.register;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.avillar.gymtracker.IntegrationBaseTest;
import org.avillar.gymtracker.authapi.domain.UserApp;
import org.avillar.gymtracker.authapi.domain.UserDao;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

class RegisterTest extends IntegrationBaseTest {

  private static final String ENDPOINT = "/auth-api/register";

  private final String USERNAME = easyRandom.nextObject(String.class);
  private final String PASSWORD = easyRandom.nextObject(String.class);

  @Autowired private UserDao userDao;

  @Value("${security.tokenType}")
  private String tokenType;

  @Value("${registerCode}")
  private String registerCode;

  @BeforeEach
  void beforeEach() {
    userDao.deleteAll();
  }

  @AfterEach
  void afterEach() {
    userDao.deleteAll();
  }

  @Test
  void registerOkTest() throws Exception {
    final JSONObject loginRequest = new JSONObject();
    loginRequest.put("username", USERNAME);
    loginRequest.put("password", PASSWORD);
    loginRequest.put("registerCode", registerCode);

    mockMvc
        .perform(post(ENDPOINT).contentType(APPLICATION_JSON).content(loginRequest.toString()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").isNotEmpty())
        .andExpect(jsonPath("$.username").value(USERNAME))
        .andExpect(jsonPath("$.type").value(tokenType))
        .andExpect(jsonPath("$.token").isNotEmpty());

    final List<UserApp> users = userDao.findAll();
    assertThat(users).size().isEqualTo(1);
    assertThat(users.get(0).getUsername()).isEqualTo(USERNAME);
  }

  @Test
  void registerKoWrongRegisterCodeTest() throws Exception {
    final JSONObject loginRequest = new JSONObject();
    loginRequest.put("username", USERNAME);
    loginRequest.put("password", PASSWORD);
    loginRequest.put("registerCode", easyRandom.nextObject(String.class));

    mockMvc
        .perform(post(ENDPOINT).contentType(APPLICATION_JSON).content(loginRequest.toString()))
        .andDo(print())
        .andExpect(status().isBadRequest());

    assertThat(userDao.findAll()).size().isEqualTo(0);
  }

  @Test
  void registerBadRequestTest() throws Exception {
    final JSONObject loginRequest = new JSONObject();
    loginRequest.put("username", USERNAME);
    loginRequest.put("password", null);
    loginRequest.put("registerCode", registerCode);

    mockMvc
        .perform(post(ENDPOINT).contentType(APPLICATION_JSON).content(loginRequest.toString()))
        .andDo(print())
        .andExpect(status().isBadRequest());

    assertThat(userDao.findAll()).size().isEqualTo(0);
  }
}
