# Der "TestScheduler" aus rxjs/testing

## Worum gehts eigentlich?
In Anwendungen die `Observables` (`rxjs`) gibt es die Herausforderung die Operationen die auf Observables ausgeführt werden zu testen.
D.h. wir haben ein (oder sogar mehrere) Observables und eine `pipe(..)` die jetzt aus diesem(/n) Observables ein

In diesem Tutorial wollen wir uns damit beschäftigen, wi

Eine von mehreren Möglichkeiten die Korrektheit einschließlich der zeitlichen Komponente von Observables zu testen ist
der `TestScheduler` aus `rxjs/testing` (Quelle: https://rxjs.dev/guide/testing/marble-testing)

Die einfachste Form eines Tests mit dem `TestScheduler` ist :
```Typescript
describe('DummyService', () => {
    let testScheduler: TestScheduler;
    beforeEach(() => {
        testScheduler = new TestScheduler((actual, expected) => {
            // asserting the two objects are equal - required
            expect(actual).toEqual(expected); // die Ausgabe hier bei Fehlern ist etwas verwirrend, das fixen wir gleich!
        });
    });
    it('Aufgabe1: simplest case to get familiar with the syntax : fix the problem', () => {
        testScheduler.run((helpers) => {
            const {cold, expectObservable} = helpers;
            const source$ = cold('  -1-|');
            const expected = '-2-|';
            expectObservable(source$).toBe(expected);
        });
    });
});
```
Kopiere den Code in ein AngularProjekt deiner Wahl (oder erzeuge ein neues), und lasse den Test laufen.
Verstehe wie `expectObservable(source$).toBe(expected)` funktioniert:
* erstes Argument ist ein (cold) Observable, welches wir mit der Hilfsfunktion `cold()` erzeugt haben.
