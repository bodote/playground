import {RunHelpers, TestScheduler} from "rxjs/testing";
import {delay, map, Observable} from "rxjs";


//Lösung zu Aufgabe 2
interface Notification {
    kind: string;
    value: string;
    error: any;
}

export interface Frame {
    frame: number;
    notification: Notification;
}

export function logFrames(label: string, frames: Frame[]) {
    console.log(label + ':');
    frames.forEach((frame: Frame) => {
        console.log(
            'Frame:',
            frame.frame,
            'Kind',
            frame.notification.kind,
            'Value:',
            frame.notification.value,
            frame.notification.error ? 'Error:' + frame.notification.error : ''
        );
    });
    console.log('----------');
}

describe('DummyService', () => {

    let testScheduler: TestScheduler;
    beforeEach(() => {

        testScheduler = new TestScheduler(function (actual: Frame[], expected: Frame[]) {
            //Aufgabe 2: implementiere logFrames , sodass du den Grund für fehlschlagende Tests auf den ersten Blick erkennst
            logFrames('actual', actual);
            logFrames('expected', expected);
            // asserting the two objects are equal - required
            expect(actual).toEqual(expected); // die Ausgabe hier bei Fehlern ist etwas verwirrend
        });
    });


    it('Aufgabe1: simplest case to get familiar with the syntax : fix the problem', () => {
        testScheduler.run((helpers: RunHelpers) => {
            let start = Date.now();
            const {cold, expectObservable, time} = helpers;
            const coldObs$ = cold('    -a--b--|',{a:1,b:2});
            const wrongExpectedPattern = '-----------c--d--|';//naive vermutung , jedoch: delay(x) in einer pipe delayed NICHT das complete!!
            const expectedPattern = '     -----------c--(d|)';//das complete-event "|" kommt stattdessen gleichzeitig mit dem letzten value
            const expectedValues = {c:3,d:6};

            //act
           let obsResult$: Observable<number> = funcToBeTested(coldObs$,10);

            expectObservable(obsResult$).toBe(expectedPattern,expectedValues);
        });
    });

    const funcToBeTested = (obs:Observable<number>,del:number ) => obs.pipe(delay(del),map(a=>a*3));

    it('Aufgabe2: simplest case but with a manualy constructed cold Observable', function (done) {
        //arrange
        let start = Date.now();
        let timeTofirstValue = 10;
        let timeToComplete = 30;
        let del = 10;
        const coldObs2$ = new Observable<number>((subscriber) => {
            setTimeout(() => {
                subscriber.next(1);
            }, timeTofirstValue);
            setTimeout(() => {
                subscriber.complete();
            }, timeToComplete);
        });

        //act
        let obs$: Observable<number> = funcToBeTested(coldObs2$,del);

        //assert
        obs$.subscribe({
            next: (value) => {
                let timePassed = Date.now() - start
                console.log("value:" + value + " time:" + (timePassed));
                // expect(timePassed).toBeGreaterThanOrEqual(timeTofirstValue+del);
                // expect(timePassed).toBeLessThan(timeTofirstValue+del+5);
                // expect(value).toBe(3);
            }, complete: () => {
                let timePassed = Date.now() - start
                console.log("complete" + " time:" + (timePassed));
                // expect(timePassed).toBeGreaterThanOrEqual(timeToComplete);
                // expect(timePassed).toBeLessThan(timeToComplete+5);
            }
        });
        setTimeout(() => {
            done()
        }, 300);
    });

});
