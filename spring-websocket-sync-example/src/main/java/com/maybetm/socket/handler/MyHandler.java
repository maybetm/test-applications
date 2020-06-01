package com.maybetm.socket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author zebzeev-sv
 * @version 29.05.2020 23:40
 */
@Service
@Slf4j
public class MyHandler extends TextWebSocketHandler
{

  private final ObjectMapper mapper;

  @Autowired
  public MyHandler(ObjectMapper mapper)
  {
    this.mapper = mapper;
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception
  {
    log.info("handleTextMessage тест; message: {}", message.getPayload());

    Thread.sleep(3000);
    session.sendMessage(new TextMessage(String.format("response; rand number: %s", Math.random())));
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception
  {
    log.info("connection new ssk; id: {}", session.getHandshakeHeaders());
  }

}
