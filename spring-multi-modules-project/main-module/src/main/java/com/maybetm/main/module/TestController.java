package com.maybetm.main.module;

import com.maybetm.service.module.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zebzeev-sv
 * @version 16.06.2020 22:39
 */
@RestController
@RequestMapping("/test")
public class TestController
{
  @Autowired
  public TestService testService;

  @GetMapping ("/getTest")
  public ResponseEntity<String> test()
  {
    return ResponseEntity.ok(testService.getTestString());
  }
}

