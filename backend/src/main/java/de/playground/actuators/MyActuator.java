package de.playground.actuators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Endpoint(id = "features")
@Slf4j
public class MyActuator {

  public MyActuator(){
    features.put("bodo","te");
  }

  private Map<String, String> features = new ConcurrentHashMap<>();


  @ReadOperation(produces="application/json")
  public Object feature(@Selector String name) {
    if (name.equals("features")) return features;
    return features.get(name);
  }

  @WriteOperation
  public void configureFeature(@Selector String name, String feature1, String feature2 ) {
    features.put("feature1",feature1);
    features.put("feature2",feature2);
    log.info("name/feature1:"+name+ "/"+ features.get("feature1"));
    log.info("name/feature2:"+name+ "/"+ features.get("feature2"));
  }





  @DeleteOperation
  public void deleteFeature(@Selector String name) {
    features.remove(name);
  }
@Data
@AllArgsConstructor
  public static class Feature {
    private String configuredLevel;
    private String otherParam;

    // [...] getters and setters
  }

}