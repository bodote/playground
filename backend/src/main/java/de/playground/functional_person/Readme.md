1. Arrays.asList -> List.of() in Compare.java ersetzten
2. stream().sorted() and toList() (hinweis auf parallelStream())
3. `public int ageDifference(final Person other) { return age - other.age; }`
4. Person::ageDifference
5. ?? reduce `.reduce(new ArrayList<Person>(), (List<Person> l, Person p) -> {
   l.add(p);
   return l;
   }, (List<Person> l1, List<Person> l2) -> {
   l1.addAll(l2);
   return l1;
   })`
6. collect(toList()) -> toList()
7. ascendingAge , descendingAge person2.ageDiffernce(person1)
8. Comparator<Person> compareAscending =...
9. Comarator.comparing(Person::getAge)
10. inline *cendingAgeList in `printPeople()`
11. alphabetisch sortieren (getName())
13. Class Person -> record Person (statt `private final`)
14. den jüngsten, den ältesten 3. Möglichkeit neben intermediate operations, und reduce-final operations die listen zurückgeben: reduce operation dir nur einen wert zurück gibt.
15. Optional<Person> bei min/max einbauen, MAP und LAZY Evaluation!
16. Multiple and Fluent Comparisons : sort based on both name and age.
17. Using the collect Method and the Collectors Class: alle jünger als 22 mit forEach() low level
18. foreach() -> collect() zuerst mit `()-> new ArrayList<Perason>`
15. dann mit toList()
16. groupingBy()  byAge
17. mit mapping byName groupingBy(byAge, mapping(byName, toList()))
18. debuggin mit peek() oder INtellijStream Debugger
19. Damit Endet Vortrag auf Seite 57, Kapitel 3 (von 223 seiten und 13 Kapiteln)
20. Ausblick:
  * map vs flapMap() anyMatch() partitioning (wie groups.size=2) sum, average etc. teeing tee()
  * beeing lazy
  * optimizing recoursion
  * error handling
  * etc... 
 
