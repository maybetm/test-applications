package com.maybetm.service.module;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zebzeev-sv
 * @version 16.06.2020 22:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class TestServiceTest
{

  @Autowired
  TestService testService;

  @Test
  void getTestString()
  {
    testService.getTestString();
  }
}
