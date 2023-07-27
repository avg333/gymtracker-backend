package org.avillar.gymtracker;

import java.util.List;
import org.apache.commons.lang3.SystemUtils;
import org.avillar.gymtracker.authapi.domain.UserApp;
import org.avillar.gymtracker.authapi.domain.UserDao;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import redis.embedded.RedisServer;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public abstract class IntegrationBaseTest {

  protected static final String USER_NAME_OK = "user_ok";
  protected static final String USER_NAME_KO = "user_ko";

  protected final EasyRandom easyRandom = new EasyRandom();

  @Autowired protected MockMvc mockMvc;
  @Autowired protected UserDao userDao;

  @Value("${spring.data.redis.port}")
  private int redisPort;

  private redis.embedded.RedisServer redisServer;

  protected void createUsers() {
    userDao.deleteAll();
    userDao.saveAll(
        List.of(
            new UserApp(null, USER_NAME_OK, easyRandom.nextObject(String.class)),
            new UserApp(null, USER_NAME_KO, easyRandom.nextObject(String.class))));
  }

  protected void deleteUsers() {
    userDao.deleteAll();
  }

  protected void startRedis() {
    redisServer =
        SystemUtils.IS_OS_WINDOWS
            ? RedisServer.builder()
                .port(redisPort)
                .setting("maxheap 128M")
                .setting("daemonize no")
                .setting("appendonly no")
                .build()
            : RedisServer.builder().build();
    redisServer.start();
  }

  protected void stopRedis() {
    if (redisServer != null && redisServer.isActive()) {
      redisServer.stop();
    }
  }
}
