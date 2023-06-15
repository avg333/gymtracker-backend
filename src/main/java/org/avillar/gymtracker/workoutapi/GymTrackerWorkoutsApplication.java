package org.avillar.gymtracker.workoutapi;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Value;

// @EnableDiscoveryClient
// @SpringBootApplication
// @ComponentScan(basePackages = "org.avillar.gymtracker")
public class GymTrackerWorkoutsApplication {

  @Value("${spring.jpa.properties.hibernate.jdbc.time_zone}")
  private String timeZone;

  //  public static void main(String[] args) {
  //    SpringApplication.run(GymTrackerWorkoutsApplication.class, args);
  //  }

  @PostConstruct
  public void init() {
    TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
  }
}
