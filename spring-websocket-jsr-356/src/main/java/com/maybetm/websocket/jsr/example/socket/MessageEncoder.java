package com.maybetm.websocket.jsr.example.socket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author zebzeev-sv
 * @version 29.05.2020 16:59
 */
public class MessageEncoder implements Encoder.Text<String>
{

  @Override
  public String encode(String message) throws EncodeException
  {
    return message;
  }

  @Override
  public void init(EndpointConfig endpointConfig)
  {

  }

  @Override
  public void destroy()
  {

  }
}
