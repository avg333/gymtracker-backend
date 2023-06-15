package org.avillar.gymtracker.authapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.authapi.auth.domain.UserApp;
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
    if (!userDao.findAll().isEmpty()) {
      log.info("La base de datos ya tiene datos. No se insertaran mas");
      return;
    }
    userDao.save(new UserApp(null,
        "chema", new BCryptPasswordEncoder().encode("chema69"), null, "Chema",
        "Garcia", "Romero", null, UserApp.GenderEnum.MALE, UserApp.ActivityLevelEnum.EXTREME));
    userDao.save(new UserApp(null,
        "alex", new BCryptPasswordEncoder().encode("alex69"), null, "Alex",
        "Garcia", "Fernandez", null, UserApp.GenderEnum.FEMALE,
        UserApp.ActivityLevelEnum.MODERATE));
    log.info("Creados dos usuarios");
  }
}