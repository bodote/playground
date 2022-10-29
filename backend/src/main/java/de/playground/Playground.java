package de.playground;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Playground {

  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(SpringApplication.class);
    SpringApplication.run(Playground.class, args);
    logger.info("Spring runs");
  }

}
