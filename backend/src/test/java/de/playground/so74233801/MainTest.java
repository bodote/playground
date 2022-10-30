package de.playground.so74233801;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class) // actually don't need it but leads to UnnecessaryStubbingsException
//@MockitoSettings(strictness = Strictness.LENIENT) // one option to suppress the UnnecessaryStubbingsException
class MainTest {
  Logger logger = LoggerFactory.getLogger(MainTest.class);
  //@Mock(lenient = true)
  @Mock(strictness = Mock.Strictness.LENIENT)
  private Service someService;
  private MainService serviceUnderTest;
  @BeforeEach
  void setup(){
    String version = System.getProperty("java.version");
    logger.info("JavaVersion="+version);
    //this.someService = Mockito.mock(Service.class);
    this.serviceUnderTest = new MainService(someService);
  }

  @ParameterizedTest
  @MethodSource("getFlags")
  void shouldTestIfFlagWorks(String someFlag) {

    // other option to suppress the UnnecessaryStubbingsException:
    // Mockito.lenient().doReturn will make the UnnecessaryStubbingsException to disappear
    // if you use @ExtendWith(MockitoExtension.class) above
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