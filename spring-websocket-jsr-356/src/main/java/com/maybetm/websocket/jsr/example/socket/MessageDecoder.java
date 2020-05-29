package com.maybetm.websocket.jsr.example.socket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * @author zebzeev-sv
 * @version 29.05.2020 17:00
 */
public class MessageDecoder implements Decoder.Text<String>
{

  @Override
  public String decode(String s) throws DecodeException
  {
    return s;
  }

  @Override
  public boolean willDecode(String s)
  {
    return s != null;
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
