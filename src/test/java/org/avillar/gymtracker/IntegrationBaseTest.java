package org.avillar.gymtracker;

import java.util.List;
import org.avillar.gymtracker.authapi.domain.UserApp;
import org.avillar.gymtracker.authapi.domain.UserApp.ActivityLevelEnum;
import org.avillar.gymtracker.authapi.domain.UserApp.GenderEnum;
import org.avillar.gymtracker.authapi.domain.UserDao;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public abstract class IntegrationBaseTest {

  protected static final String USER_NAME_OK = "user_ok";
  protected static final String USER_NAME_KO = "user_ko";

  protected final EasyRandom easyRandom = new EasyRandom();

  private redis.embedded.RedisServer redisServer;

  @Autowired protected UserDao userDao;

  public void createUsers() {
    userDao.deleteAll();
    userDao.saveAll(
        List.of(
            new UserApp(
                null,
                USER_NAME_OK,
                easyRandom.nextObject(String.class),
                null,
                null,
                null,
                null,
                null,
                GenderEnum.MALE,
                ActivityLevelEnum.MODERATE),
            new UserApp(
                null,
                USER_NAME_KO,
                easyRandom.nextObject(String.class),
                null,
                null,
                null,
                null,
                null,
                GenderEnum.MALE,
                ActivityLevelEnum.EXTREME)));
  }

  public void deleteUsers() {
    userDao.deleteAll();
  }

  public void startRedis() {
    redisServer = new redis.embedded.RedisServer(6379); // FIXME poner el puerto
    redisServer.start();
  }

  public void stopRedis() {
    if (redisServer != null) {
      redisServer.stop();
    }
  }
}
