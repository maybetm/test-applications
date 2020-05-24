package com.maybetm;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zebzeev-sv
 * @version 17.05.2020 15:13
 */
@ComponentScan ("com.maybetm")
public class AppMain
{

  public static void main(String... args)
  {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppMain.class);

    UserServiceController controller = context.getBean(UserServiceController.class);
    controller.print();
  }
}
