package com.maybetm.socket.configuration;

import com.maybetm.socket.handler.MyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * @author zebzeev-sv
 * @version 29.05.2020 23:41
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer
{
  private final MyHandler myHandler;

  @Autowired
  public WebSocketConfig(MyHandler myHandler)
  {
    this.myHandler = myHandler;
  }

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry)
  {
    registry
      .addHandler(myHandler, "/myHandler")
      .addInterceptors(new HttpSessionHandshakeInterceptor());
  }

  @Bean
  public ServletServerContainerFactoryBean createWebSocketContainer() {
    ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
    container.setMaxTextMessageBufferSize(8192);
    container.setMaxBinaryMessageBufferSize(8192);
    return container;
  }

}
