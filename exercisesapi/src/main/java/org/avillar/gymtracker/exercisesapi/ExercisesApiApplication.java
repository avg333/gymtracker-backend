package org.avillar.gymtracker.exercisesapi;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExercisesApiApplication {

  @Value("${spring.jpa.properties.hibernate.jdbc.time_zone}")
  private String timeZone;

  public static void main(String[] args) {
    SpringApplication.run(ExercisesApiApplication.class, args);
  }

  @PostConstruct
  public void init() {
    TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
  }
}
