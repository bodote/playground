# Buchtip

* [Functional Programming in Java, Second Edition siehe](smb://ba-server-048.office.brand-ad.de/Groups/SoftwareEntwicklung/bücher_und_artikel/2023/functional-programming-in-java-second-edition_P1.0.*)

# Warum?

## Bisher:

Declarativ:

```
boolean found = false; 
for(String city : cities) {
  if(city.equals("Chicago")) { 
    found = true;
    break;
  } 
}
System.out.println("Found chicago?:" + found);
```

## besser:

Imperativ:

```
System.out.println("Found chicago?:" + cities.contains("Chicago"));
```

## Weil:

### Frage an ChatGPT:

"Was sind die Hauptvorteile des funktionalen Programmierstils in Java und die Vorteile des deklarativen Stils gegenüber
dem imperativen Stil?"

### Antwort:

(minimal editiert)
**Funktionale Programmierung in Java, insbesondere mit der Einführung von Lambdas und Streams in Java 8, hat mehrere
Vorteile:**

* Unveränderlichkeit: Funktionale Programmierung fördert oft die Verwendung von unveränderlichen Datenstrukturen. Dies
  führt zu weniger Nebenwirkungen und daher zu weniger Fehlern, besonders in nebenläufigen oder Umgebungen (mit mehr als
  einem Thread).
* Prägnanz: Funktionaler Code neigt dazu, prägnanter zu sein, da er überflüssigen Code minimiert. Man drückt oft aus,
  was man erreichen möchte, ohne schreiben zu müssen, wie man es Schritt für Schritt erreicht.
* Leichtere Nachvollziehbarkeit: Ohne Seiteneffekte ist das Verhalten von reinen Funktionen (ein Kernkonzept in der
  funktionalen Programmierung) vorhersagbar und leichter zu verstehen.
* Higher Order Functions: Sie können Funktionen als Argumente übergeben, sie zurückgeben oder in Datenstrukturen
  speichern, was zu hochgradig generischem und wiederverwendbarem Code führt.
* Parallelität: Funktionale Codes lassen sich leichter parallelisieren, da reine Funktionen keinen Zustand verändern
  oder Seiteneffekte haben. Das bedeutet, dass sie parallel ausgeführt werden können, ohne sich um die Thread-Sicherheit
  von geteilten veränderlichen Daten kümmern zu müssen.
* Lazy Evaluation: Funktionale Programmierung verwendet oft Lazy Evaluation, was bedeutet, dass Berechnungen
  aufgeschoben werden, bis ihre Ergebnisse benötigt werden. Dies kann zu Leistungsverbesserungen führen, da unnötige
  Berechnungen vermieden werden können.

**Deklarative Programmierung:**

* Was statt Wie: Sie schreiben Code, der angibt, was Sie erreichen möchten, nicht wie Sie es erreichen möchten. SQL ist
  ein gutes Beispiel dafür – Sie deklarieren, was Sie aus einer Datenbank abrufen möchten, ohne die Schritte des
  Abrufens zu skizzieren.
* Wartbarkeit: Da deklarativer Code tendenziell höherstufig ist, ist er oft einfacher zu lesen und zu warten.
* Minimierte Seiteneffekte: Ähnlich wie bei der funktionalen Programmierung reduziert sie oft Seiteneffekte, was sie
  vorhersehbarer macht.

**Imperative Programmierung:**

* Kontrolle: Sie haben detaillierte Kontrolle über die Schritte, die Ihr Programm unternimmt. Dies kann ein Vorteil
  sein, wenn es um Leistungsoptimierung geht, da Sie niedrigstufige Entscheidungen über den Ablauf Ihres Programms
  treffen können.
* Allgegenwart: Es ist der traditionelle Stil der Programmierung, der vielen von Anfang an beigebracht wird, so dass es
  eine breite Wissensbasis und viele verfügbare Ressourcen gibt.
* Explizitheit: Für einige ist es leichter zu verstehen, weil jeder Schritt dargelegt ist, was den Fluss der Logik
  klarer erscheinen lassen kann.

Zusammenfassend kann gesagt werden, dass **funktionale** und **deklarative Stile** zu lesbarem und wartbarem Code mit
potenziell weniger Fehlern führen können, während der **imperative Stil** mehr Kontrolle bietet und oft intuitiver für
diejenigen ist, die einen Hintergrund in der traditionellen Programmierung haben. Es ist nicht immer eine Frage davon,
dass einer besser als der andere ist; es hängt vielmehr davon ab, welches Problem vorliegt und in welchem Kontext man
arbeitet.





