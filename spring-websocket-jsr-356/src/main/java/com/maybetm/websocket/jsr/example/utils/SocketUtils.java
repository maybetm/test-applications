package com.maybetm.websocket.jsr.example.utils;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Set;

/**
 * @author zebzeev-sv
 * @version 29.05.2020 16:41
 */
public class SocketUtils
{

  public static void broadcast(String message, Set<Session> listeners)
  {
    for (Session session: listeners) {
      sendMessage(message, session);
    }
  }

  public static void sendMessage(String message, Session session)
  {
    try {
      session.getBasicRemote().sendText(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
