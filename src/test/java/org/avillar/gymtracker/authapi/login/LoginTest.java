package org.avillar.gymtracker.authapi.login;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.avillar.gymtracker.IntegrationBaseTest;
import org.avillar.gymtracker.authapi.domain.UserApp;
import org.avillar.gymtracker.authapi.domain.UserDao;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class LoginTest extends IntegrationBaseTest {

  private static final String ENDPOINT = "/auth-api/signin";

  private final String USERNAME = easyRandom.nextObject(String.class);
  private final String PASSWORD = easyRandom.nextObject(String.class);

  @Autowired private UserDao userDao;

  @BeforeEach
  void beforeEach() {
    userDao.deleteAll();
    userDao.save(new UserApp(null, USERNAME, new BCryptPasswordEncoder().encode(PASSWORD)));
  }

  @AfterEach
  void afterEach() {
    userDao.deleteAll();
  }

  @Test
  void loginOkTest() throws Exception {
    final UserApp userApp = userDao.findAll().get(0);

    final JSONObject loginRequest = new JSONObject();
    loginRequest.put("username", USERNAME);
    loginRequest.put("password", PASSWORD);

    mockMvc
        .perform(post(ENDPOINT).contentType(APPLICATION_JSON).content(loginRequest.toString()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(userApp.getId().toString()))
        .andExpect(jsonPath("$.username").value(userApp.getUsername()))
        .andExpect(jsonPath("$.token").isNotEmpty());
  }

  @Test
  void loginKoTest() throws Exception {
    final JSONObject loginRequest = new JSONObject();
    loginRequest.put("username", USERNAME);
    loginRequest.put("password", easyRandom.nextObject(String.class));

    mockMvc
        .perform(post(ENDPOINT).contentType(APPLICATION_JSON).content(loginRequest.toString()))
        .andDo(print())
        .andExpect(status().isForbidden());
  }

  @Test
  void loginBadRequestTest() throws Exception {
    final JSONObject loginRequest = new JSONObject();
    loginRequest.put("username", USERNAME);
    loginRequest.put("password", null);

    mockMvc
        .perform(post(ENDPOINT).contentType(APPLICATION_JSON).content(loginRequest.toString()))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }
}
