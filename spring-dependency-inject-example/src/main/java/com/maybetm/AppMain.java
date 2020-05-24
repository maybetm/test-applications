package com.maybetm;

import com.maybetm.configuration.Config;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zebzeev-sv
 * @version 17.05.2020 15:13
 */
public class AppMain
{

  public static void main(String... args)
  {
    ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

    UserServiceController controller = context.getBean(UserServiceController.class);
    controller.print();
  }
}
