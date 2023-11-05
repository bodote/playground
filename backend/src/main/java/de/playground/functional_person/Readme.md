1. Arrays.asList -> List.of()
2. stream().sorted() and toList()
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
9. inline *cendingAgeList in `printPeople()`
10. alphabetisch sortieren
11. Class Person -> record Person (statt `private final`)
12. den jüngsten, den ältesten
13. Multiple and Fluent Comparisons : sort based on both name and age.
14. Using the collect Method and the Collectors Class: alle jünger als 22 mit forEach() low level
15. foreach() -> collect() zuerst mit `()-> new ArrayList<Perason>`
15. dann mit toList()
16. groupingBy()  byAge
17. mit mapping byName groupingBy(byAge, mapping(byName, toList()))