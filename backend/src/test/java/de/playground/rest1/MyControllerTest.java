package de.playground.rest1;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = MyController.class)
@ContextConfiguration(classes = { MyController.class })
@TestPropertySource(locations  =  "/application.properties")
@Slf4j
class MyControllerTest {
  @Autowired
  private MockMvc mockMvc;


  @Test
  void myController() throws Exception {
    log.error("start-------------");
    mockMvc.perform(get("/myentity")).andDo(MockMvcResultHandlers.log())
            .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
            .andExpect(jsonPath("$.content", Matchers.equalTo("World")));
    log.debug("end-------------");
  }
}