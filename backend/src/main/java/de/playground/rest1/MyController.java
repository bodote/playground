package de.playground.rest1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MyController {

  private final AtomicLong counter = new AtomicLong();

  @GetMapping("/myentity")
  public MyEntity greeting(@RequestParam(value = "content", defaultValue = "World") String content) {
    return new MyEntity(counter.addAndGet(1),content);
  }
}