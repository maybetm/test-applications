package com.maybetm.web.socket.example.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zebzeev-sv
 * @version 27.05.2020 18:50
 */
@Setter
@Getter
@NoArgsConstructor
public class ChatMessage
{
  private MessageType type;
  private String content;
  private String sender;
}
