package com.maybetm.user;

/**
 * @author zebzeev-sv
 * @version 17.05.2020 14:41
 */
public class User
{
  private final Long id;

  private final String name;

  public User(Long id, String name)
  {
    this.id = id;
    this.name = name;
  }

  @Override
  public String toString()
  {
    return "User{" +
           "id=" + id +
           ", name='" + name + '\'' +
           '}';
  }
}
