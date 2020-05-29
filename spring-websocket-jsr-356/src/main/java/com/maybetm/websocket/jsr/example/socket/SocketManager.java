package com.maybetm.websocket.jsr.example.socket;

import javax.websocket.Session;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author zebzeev-sv
 * @version 29.05.2020 18:50
 */
public class SocketManager
{
  public static final Set<Session> sessions = new CopyOnWriteArraySet<>();

}
