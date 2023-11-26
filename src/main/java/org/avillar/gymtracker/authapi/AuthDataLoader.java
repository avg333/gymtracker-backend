package org.avillar.gymtracker.authapi;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.authapi.common.adapter.repository.UserDao;
import org.avillar.gymtracker.authapi.common.adapter.repository.model.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthDataLoader implements ApplicationRunner {

  private static final List<String> DEFAULT_USERS = List.of("chema", "alex", "adrian");
  private static final String PASSWORD_SUFFIX = "69";

  private final UserDao userDao;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Value("${spring.profiles.active}")
  private String activeProfile;

  private List<UserEntity> generateUsers() {
    final List<UserEntity> users = new ArrayList<>(DEFAULT_USERS.size());

    for (final String username : DEFAULT_USERS) {
      users.add(
          new UserEntity(null, username, bCryptPasswordEncoder.encode(username + PASSWORD_SUFFIX)));
    }

    return users;
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
    final List<UserEntity> users = generateUsers();

    log.info("\tInserting {} users...", users.size());
    userDao.saveAll(users);
    log.info("\tInserted {} users", users.size());

    long finish = System.currentTimeMillis();
    log.info("Populated auth micro with {} entities in {} ms", users.size(), finish - start);
  }
}
