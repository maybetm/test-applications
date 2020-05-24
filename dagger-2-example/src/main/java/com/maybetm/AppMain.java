package com.maybetm;

import com.maybetm.configuration.DaggerUserServiceComponent;
import com.maybetm.configuration.UserServiceComponent;
import com.maybetm.user.UserService;

/**
 * @author zebzeev-sv
 * @version 17.05.2020 14:41
 */
public class AppMain
{

  public static void main(String... args)
  {
    UserServiceComponent component = DaggerUserServiceComponent.create();

    UserService userService = component.provideUserService();

    userService.doPrint();

  }
}
