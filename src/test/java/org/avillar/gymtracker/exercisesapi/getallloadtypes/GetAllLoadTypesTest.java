package org.avillar.gymtracker.exercisesapi.getallloadtypes;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import org.avillar.gymtracker.authapi.domain.UserApp;
import org.avillar.gymtracker.authapi.domain.UserApp.ActivityLevelEnum;
import org.avillar.gymtracker.authapi.domain.UserApp.GenderEnum;
import org.avillar.gymtracker.authapi.domain.UserDao;
import org.avillar.gymtracker.exercisesapi.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.domain.LoadTypeDao;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class GetAllLoadTypesTest {

  private static final String USER_NAME_OK = "adrian";
  private final EasyRandom easyRandom = new EasyRandom();
  @Autowired private MockMvc mockMvc;
  @Autowired private LoadTypeDao loadTypeDao;
  @Autowired private UserDao userDao;

  @BeforeAll
  public void before() {
    userDao.deleteAll();
    userDao.saveAll(
        List.of(
            new UserApp(
                null,
                "chema",
                new BCryptPasswordEncoder().encode("chema69"),
                null,
                "Chema",
                "Garcia",
                "Romero",
                null,
                GenderEnum.MALE,
                ActivityLevelEnum.EXTREME),
            new UserApp(
                null,
                "alex",
                new BCryptPasswordEncoder().encode("alex69"),
                null,
                "Alex",
                "Garcia",
                "Fernandez",
                null,
                GenderEnum.FEMALE,
                ActivityLevelEnum.SEDENTARY),
            new UserApp(
                null,
                "adrian",
                new BCryptPasswordEncoder().encode("adrian69"),
                null,
                "Adrian",
                "Villar",
                "Gesto",
                null,
                GenderEnum.MALE,
                ActivityLevelEnum.MODERATE)));
  }

  @AfterAll
  public void afterAll() {
    userDao.deleteAll();
  }

  @BeforeEach
  void beforeEach() {
    loadTypeDao.deleteAll();
    final List<LoadType> loadTypes = easyRandom.objects(LoadType.class, 10).toList();
    loadTypes.forEach(
        loadType -> {
          loadType.setId(null);
          loadType.setExercises(new HashSet<>());
        });
    loadTypeDao.saveAll(loadTypes);
  }

  @AfterEach
  void afterEach() {
    loadTypeDao.deleteAll();
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void getAllLoadTypes() throws Exception {
    mockMvc
        .perform(get("/exercises-api/loadTypes"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", hasSize(10)));
  }

  @TestConfiguration
  public static class EmbeddedRedisTestConfiguration {

    private final redis.embedded.RedisServer redisServer;

    public EmbeddedRedisTestConfiguration(@Value("${spring.data.redis.port}") final int redisPort)
        throws IOException {
      this.redisServer = new redis.embedded.RedisServer(redisPort);
    }

    @PostConstruct
    public void startRedis() {
      this.redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
      this.redisServer.stop();
    }
  }
}
