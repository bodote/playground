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
    features.put("bodo",new Feature("true","other"));
  }

  private Map<String, Feature> features = new ConcurrentHashMap<>();

  @ReadOperation
  public Map<String, Feature> features() {
    return features;
  }

  @ReadOperation
  public Feature feature(@Selector String name) {
    return features.get(name);
  }

  @WriteOperation
  public void configureFeature(@Selector String name, Feature feature) {
    log.info("name/feature:"+name+ "/"+feature.toString());
    //features.put(name, feature.toString());
  }





  @DeleteOperation
  public void deleteFeature(@Selector String name) {
    features.remove(name);
  }
@Data
@AllArgsConstructor
  public static class Feature {
    //private boolean enabled;
    private String configuredLevel;
    private String otherParam;

    // [...] getters and setters
  }

}