package de.playground.so74233801;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;


class MainTest {
  Logger logger = LoggerFactory.getLogger(MainTest.class);
  private Service someService;
  private MainService serviceUnderTest;
  @BeforeEach
  void setup(){
    String version = System.getProperty("java.version");
    logger.info("JavaVersion="+version);
    Class<Service> clazz = Service.class;
    this.someService = Mockito.mock(clazz);
    this.serviceUnderTest = new MainService(someService);
  }

  @ParameterizedTest
  @MethodSource("getFlags")
  void shouldTestIfFlagWorks(String someFlag) {
    // Given
    Mockito.doReturn(true).when(someService).execute(someFlag);

    // When
    Throwable thrown = Assertions.catchThrowable(() -> serviceUnderTest.process(someFlag));

    // Then
    Assertions.assertThat(thrown).hasMessage("Oh no, exception!");
  }

  private static Stream<Arguments> getFlags() {
    return Stream.of(
            Arguments.of("flag1"),
            Arguments.of("flag2")
    );
  }
}