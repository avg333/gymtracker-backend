package org.avillar.gymtracker.authapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.authapi.auth.domain.UserApp;
import org.avillar.gymtracker.authapi.auth.domain.UserApp.ActivityLevelEnum;
import org.avillar.gymtracker.authapi.auth.domain.UserApp.GenderEnum;
import org.avillar.gymtracker.authapi.auth.domain.UserDao;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthDataLoader implements ApplicationRunner {

  private final UserDao userDao;

  public void run(ApplicationArguments args) {
    final long start = System.currentTimeMillis();
    if (!userDao.findAll().isEmpty()) {
      log.info("Micro auth is already populated");
      return;
    }
    log.info("Populating auth micro...");

    var user1 =
        new UserApp(
            null,
            "chema",
            new BCryptPasswordEncoder().encode("chema69"),
            null,
            "Chema",
            "Garcia",
            "Romero",
            null,
            UserApp.GenderEnum.MALE,
            UserApp.ActivityLevelEnum.EXTREME);
    var user2 =
        new UserApp(
            null,
            "alex",
            new BCryptPasswordEncoder().encode("alex69"),
            null,
            "Alex",
            "Garcia",
            "Fernandez",
            null,
            UserApp.GenderEnum.FEMALE,
            ActivityLevelEnum.SEDENTARY);
    var user3 =
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
            ActivityLevelEnum.MODERATE);
    var users = List.of(user1, user2, user3);

    log.info("\tInserting " + users.size() + " users...");
    userDao.saveAll(users);
    log.info("\tInserted " + users.size() + " users");

    long finish = System.currentTimeMillis();
    log.info(
        "Populated auth micro with " + users.size() + " enitities in " + (finish - start) + "ms");
  }
}
