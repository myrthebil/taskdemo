package se.myrthe.taskservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("se.myrthe.commonmodel")
public class TaskServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(TaskServiceApplication.class, args);
  }
}
