package com.jorge.directory.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class DirectoryApplication {
  public static void main(String... args) {
    SpringApplication app = new SpringApplication(DirectoryApplication.class);
    app.setShowBanner(false);
    app.setAdditionalProfiles("dev");
    app.run(args);
  }
}
