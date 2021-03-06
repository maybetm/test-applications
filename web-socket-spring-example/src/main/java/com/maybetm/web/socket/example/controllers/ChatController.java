package com.maybetm.web.socket.example.controllers;

import com.maybetm.web.socket.example.data.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.Objects;

/**
 * @author zebzeev-sv
 * @version 27.05.2020 18:52
 */
@Controller
public class ChatController
{

  @MessageMapping ("/chat.sendMessage")
  @SendTo ("/topic/public")
  public ChatMessage sendMessage(@Payload ChatMessage chatMessage)
  {
    return chatMessage;
  }

  @MessageMapping ("/chat.addUser")
  @SendTo ("/topic/public")
  public ChatMessage addUser(@Payload ChatMessage chatMessage,
                             SimpMessageHeaderAccessor headerAccessor)
  {
    // Add username in web socket session
    Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", chatMessage.getSender());
    return chatMessage;
  }
}
