package de.playground.functional_person;

import lombok.Getter;

@Getter
public class Person {
  private final String name;
  private final int age;

  public Person(final String theName, final int theAge) {
    name = theName;
    age = theAge;
  }

  public String toString() {
    return String.format("%s - %d", name, age);
  }
}
