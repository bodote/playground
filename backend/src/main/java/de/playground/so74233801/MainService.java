package de.playground.so74233801;



import java.util.Objects;


public class MainService {

  private Service someService;

  public MainService(Service service) {
    this.someService = service;
  }

  public boolean process(String flag) {
    if (Objects.equals(flag, "flag1")) {
      throw new IllegalArgumentException("Oh no, exception!");
    }
    boolean result = someService.execute(flag);
    //boolean result = true;
    if (result) {
      throw new IllegalArgumentException("Oh no, exception!");
    }
    return result;
  }
}
