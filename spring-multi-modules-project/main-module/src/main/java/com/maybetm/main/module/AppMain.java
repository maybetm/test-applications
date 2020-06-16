package com.maybetm.main.module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zebzeev-sv
 * @version 16.06.2020 22:38
 */
@SpringBootApplication (scanBasePackages = {"com.maybetm.service.module",
                                            "com.maybetm.main.module"})
public class AppMain
{

  public static void main(String... args)
  {
    SpringApplication.run(AppMain.class, args);
  }
}
