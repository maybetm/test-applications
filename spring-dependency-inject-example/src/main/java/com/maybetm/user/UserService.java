package com.maybetm.user;

import org.springframework.stereotype.Service;

/**
 * @author zebzeev-sv
 * @version 17.05.2020 15:17
 */
@Service
public class UserService implements IUserService
{

  public void print()
  {
    System.out.println("test");
  }
}
