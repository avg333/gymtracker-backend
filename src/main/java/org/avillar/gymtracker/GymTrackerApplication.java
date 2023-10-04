package org.avillar.gymtracker;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "org.avillar.gymtracker")
@EnableFeignClients
public class GymTrackerApplication {

  @Value("${spring.jpa.properties.hibernate.jdbc.time_zone}")
  private String timeZone;

  public static void main(String[] args) {
    SpringApplication.run(GymTrackerApplication.class, args);
  }

  @PostConstruct
  public void init() {
    TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
  }
}
