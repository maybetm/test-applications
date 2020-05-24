package com.maybetm.user;

import javax.inject.Inject;

/**
 * @author zebzeev-sv
 * @version 17.05.2020 14:42
 */
public class UserService
{
  private final User user;

  @Inject
  public UserService(User user)
  {
    this.user = user;
  }

  public void doPrint() {
      System.out.println(user.toString());
  }
}
