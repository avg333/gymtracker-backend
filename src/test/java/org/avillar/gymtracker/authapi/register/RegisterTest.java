package org.avillar.gymtracker.authapi.register;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import org.avillar.gymtracker.authapi.common.adapter.repository.UserDao;
import org.avillar.gymtracker.authapi.common.adapter.repository.model.UserEntity;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@Execution(ExecutionMode.SAME_THREAD)
@Sql(scripts = "classpath:authapi/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class RegisterTest {

  private static final String PATH = "/api/v1/register";

  private static final String EXISTENT_USER_NAME = "IT_TEST_USER_OK";

  @Autowired private MockMvc mockMvc;
  @Autowired private UserDao userDao;
  @Autowired private BCryptPasswordEncoder passwordEncoder;

  @Value("${security.tokenType}")
  private String tokenType;

  @Value("${registerCode}")
  private String registerCode;

  private JSONObject getJsonObject() throws JSONException {
    final JSONObject loginRequest = new JSONObject();
    loginRequest.put("username", "USERNAME");
    loginRequest.put("password", "PASSWORD");
    loginRequest.put("registerCode", registerCode);
    return loginRequest;
  }

  @Test
  void shouldRegisterUserSuccessfullyAndReturnLoginData() throws Exception {

    final int userSize = userDao.findAll().size();

    final JSONObject loginRequest = getJsonObject();
    final String username = (String) loginRequest.get("username");
    final String password = (String) loginRequest.get("password");
    mockMvc
        .perform(post(PATH).contentType(APPLICATION_JSON).content(loginRequest.toString()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").isNotEmpty())
        .andExpect(jsonPath("$.username").value(username))
        .andExpect(jsonPath("$.type").value(tokenType))
        .andExpect(jsonPath("$.token").isString());

    assertThat(userDao.findAll()).size().isEqualTo(userSize + 1);

    final List<UserEntity> users = userDao.findAll();
    final Optional<UserEntity> optionalUser =
        users.stream().filter(u -> u.getUsername().equals(username)).findAny();
    assertThat(optionalUser).isPresent();
    final UserEntity user = optionalUser.get();
    assertThat(user.getId()).isNotNull();
    assertThat(user.getUsername()).isEqualTo(username);
    assertThat(passwordEncoder.matches(password, user.getPassword())).isTrue();
  }

  @Test
  void shouldNotRegisterUserAndReturnBadRequestWhenRegisterCodeIsNotValid() throws Exception {

    final int userSize = userDao.findAll().size();

    final JSONObject loginRequest = getJsonObject();
    loginRequest.put("registerCode", Instancio.create(String.class));

    mockMvc
        .perform(post(PATH).contentType(APPLICATION_JSON).content(loginRequest.toString()))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.message").value("Wrong registration code"))
        .andExpect(jsonPath("$.validationErrors").isEmpty());

    assertThat(userDao.findAll()).size().isEqualTo(userSize);
  }

  @Test
  void shouldNotRegisterUserAndReturnBadRequestWhenUsernameAlreadyExists() throws Exception {

    final int userSize = userDao.findAll().size();

    final JSONObject loginRequest = getJsonObject();
    loginRequest.put("username", EXISTENT_USER_NAME);

    mockMvc
        .perform(post(PATH).contentType(APPLICATION_JSON).content(loginRequest.toString()))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.message").value("Username is already in use"))
        .andExpect(jsonPath("$.validationErrors").isEmpty());

    assertThat(userDao.findAll()).size().isEqualTo(userSize);
  }

  @Test
  void shouldNotRegisterUserAndReturnBadRequestWhenRequestIsNotValid() throws Exception {

    final int userSize = userDao.findAll().size();

    final JSONObject loginRequest = getJsonObject();
    loginRequest.remove("password");

    mockMvc
        .perform(post(PATH).contentType(APPLICATION_JSON).content(loginRequest.toString()))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.message").value("Invalid request content."))
        .andExpect(jsonPath("$.validationErrors").isArray())
        .andExpect(jsonPath("$.validationErrors[0].field").value("password"))
        .andExpect(jsonPath("$.validationErrors[0].message").value("The password is required"));

    assertThat(userDao.findAll()).size().isEqualTo(userSize);
  }
}
