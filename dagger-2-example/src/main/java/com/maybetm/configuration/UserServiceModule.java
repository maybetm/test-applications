package com.maybetm.configuration;

import com.maybetm.user.User;
import com.maybetm.user.UserService;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * @author zebzeev-sv
 * @version 17.05.2020 14:46
 */
@Module
public class UserServiceModule
{

  @Provides
  @Singleton
  public UserService provideUserService()
  {
    return new UserService(new User(12L, "First name"));
  }
}
