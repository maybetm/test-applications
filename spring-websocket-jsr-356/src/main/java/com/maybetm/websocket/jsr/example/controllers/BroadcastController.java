package com.maybetm.websocket.jsr.example.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybetm.websocket.jsr.example.socket.SocketManager;
import com.maybetm.websocket.jsr.example.utils.SocketUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zebzeev-sv
 * @version 29.05.2020 17:10
 */
@RestController
@RequestMapping (value = "/broadcast")
public class BroadcastController
{
  private final static ObjectMapper om = new ObjectMapper();

  @GetMapping
  public ResponseEntity<String> broadcast(@RequestParam("message") String message) throws JsonProcessingException
  {
    SocketUtils.broadcast(message, SocketManager.sessions);

    return ResponseEntity.ok(String.format("Operation complete!; listeners: %s", SocketManager.sessions.size()));
  }
}
