package org.avillar.gymtracker.authapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.authapi.domain.UserApp;
import org.avillar.gymtracker.authapi.domain.UserDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthDataLoader implements ApplicationRunner {

  @Value("${spring.profiles.active}")
  private String activeProfile;

  private final UserDao userDao;

  private static List<UserApp> generateUsers() {
    var user1 = new UserApp(null, "chema", new BCryptPasswordEncoder().encode("chema69"));
    var user2 = new UserApp(null, "alex", new BCryptPasswordEncoder().encode("alex69"));
    var user3 = new UserApp(null, "adrian", new BCryptPasswordEncoder().encode("adrian69"));
    return List.of(user1, user2, user3);
  }

  public void run(ApplicationArguments args) {
    final long start = System.currentTimeMillis();
    if (activeProfile.equals("test")) {
      return;
    } else if (!userDao.findAll().isEmpty()) {
      log.info("Micro auth is already populated");
      return;
    }

    log.info("Populating auth micro...");
    final List<UserApp> users = generateUsers();

    log.info("\tInserting {} users...", users.size());
    userDao.saveAll(users);
    log.info("\tInserted {} users", users.size());

    long finish = System.currentTimeMillis();
    log.info("Populated auth micro with {} entities in {} ms", users.size(), finish - start);
  }
}
