package com.maybetm;

import com.maybetm.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zebzeev-sv
 * @version 17.05.2020 15:18
 */
@Service
public class UserServiceController
{
  private final UserService userService;

  @Autowired
  public UserServiceController(UserService userService)
  {
    this.userService = userService;
  }

  public void print()
  {
    userService.print();
  }
}
