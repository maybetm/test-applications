package com.maybetm.configuration;

import com.maybetm.user.UserService;
import dagger.Component;

import javax.inject.Singleton;

/**
 * @author zebzeev-sv
 * @version 17.05.2020 14:48
 */
@Singleton
@Component(modules = UserServiceModule.class)
public interface UserServiceComponent
{
  UserService provideUserService();
}
