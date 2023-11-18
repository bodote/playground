1. Arrays.asList -> List.of()
2. stream().sorted() and toList()
3. `public int ageDifference(final Person other) { return age - other.age; }`
4. Person::ageDifference
5. foreach() -> collect() zuerst mit `()-> new ArrayList<Perason>`
6.

```java
people.stream().reduce(new ArrayList<Person>(),
        (List<Person> l,Person p)->{
        l.add(p);
        return l;
        },
        (List<Person> l1,List<Person> l2)->{
        l1.addAll(l2);
        return l1;
        })
```

7. collect(toList()) -> toList()
8. ascendingAge , descendingAge person2.ageDiffernce(person1)
9. Comparator<Person> compareAscending =...
10. inline *cendingAgeList in `printPeople()`
11. alphabetisch sortieren
12. Class Person -> record Person (statt `private final`)
13. den jüngsten, den ältesten
14. Multiple and Fluent Comparisons : sort based on both name and age.
15. printPeople for-loop -> stream
16. den jüngsten, den ältesten 3. Möglichkeit neben intermediate operations, und reduce-final operations die listen
    zurückgeben: reduce operation die nur einen wert zurück gibt.
17. Optional<Person> bei min/max einbauen, MAP und LAZY Evaluation!
18. Using the collect Method and the Collectors Class: alle jünger als 22 mit forEach() low level
19. groupingBy()  byAge (seite 58)
20. zusätzlich: nur die Namen ausgeben obwohl weiterhing mit grouping by age mit mapping
    byName `groupingBy(byAge, mapping(byName, toList()))`
21. debuggin mit peek() oder INtellijStream Debugger
22. Stream Debugger VORSICHT : `Collectors` ausschreiben , sonst geht der Debugger nicht
19. Damit Endet Vortrag auf Seite 57, Kapitel 3 (von 223 seiten und 13 Kapiteln)
20. Ausblick:

* map vs flapMap() anyMatch() partitioning (wie groups.size=2) sum, average etc. teeing tee()
* beeing lazy
* optimizing recoursion
* error handling
* etc... 