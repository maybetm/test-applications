package com.maybetm.socket.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zebzeev-sv
 * @version 30.05.2020 0:20
 */
@Configuration
public class AppConfig
{

  @Bean
  public ObjectMapper om()
  {
    return new ObjectMapper();
  }
}
