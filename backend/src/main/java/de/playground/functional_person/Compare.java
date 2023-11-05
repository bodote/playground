package de.playground.functional_person;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class Compare {
  static final List<Person> people = Arrays.asList(new Person("John", 20),
          new Person("Sara", 21), new Person("Jane", 21), new Person("Greg", 35));

  public static void main(String[] args) {
    people.sort((person1, person2) -> person1.getAge() - person2.getAge());
    printPeople("Sorted in ascending order by age: ", people);
  }

  public static void printPeople(final String message, final List<Person> people) {
    log.info(message);
    people.forEach(msg -> log.info(msg.toString()));
  }
}
