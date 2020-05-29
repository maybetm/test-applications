package com.maybetm.websocket.jsr.example.socket;

import org.springframework.stereotype.Component;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author zebzeev-sv
 * @version 29.05.2020 16:23
 */
@Component
@ServerEndpoint (value = "/webSocket", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class Socket extends Endpoint
{

  @Override
  public void onOpen(Session session, EndpointConfig config)
  {
    SocketManager.sessions.add(session);
  }

  @Override
  public void onClose(Session session, CloseReason closeReason)
  {
    SocketManager.sessions.remove(session);
  }

  @OnMessage
  public void onMessage(Session session, String msg) {
    try {
      session.getBasicRemote().sendText(msg + Math.random());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

}


