package com.maybetm.web.socket.example.controllers;

import com.maybetm.web.socket.example.data.ChatMessage;
import com.maybetm.web.socket.example.data.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import static java.util.Objects.requireNonNull;

/**
 * @author zebzeev-sv
 * @version 27.05.2020 18:53
 */
@Component
public class WebSocketEventListener
{

  private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

  private final SimpMessageSendingOperations messagingTemplate;

  @Autowired
  public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate)
  {
    this.messagingTemplate = messagingTemplate;
  }

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event)
  {
    logger.info("Received a new web socket connection");
  }

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event)
  {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    String username = String.valueOf(requireNonNull(headerAccessor.getSessionAttributes()).get("username"));

    if (username != null) {
      logger.info("User Disconnected : {}", username);

      ChatMessage chatMessage = new ChatMessage();
      chatMessage.setType(MessageType.LEAVE);
      chatMessage.setSender(username);

      messagingTemplate.convertAndSend("/topic/public", chatMessage);
    } else {
      logger.info("User Disconnected");
    }
  }
}
