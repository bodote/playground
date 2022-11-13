package de.playground.so74236327;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
public class ExampleProductionCode {
    List<Future<Class>> futureData;
    EnginesData enginesData;
    MyService myService;
    public ExampleProductionCode(MyService myService) {
        this.myService = myService;
        futureData = new ArrayList<Future<Class>>();
        enginesData = new EnginesData();
        Future<Class> oneFutureData = createAndStartAsyncTask();
        futureData.add(oneFutureData);
        Future<Class> twoFutureData = createAndStartFutureTask();
        futureData.add(twoFutureData);
    }
    public EnginesData getEngineData() {
        return enginesData;
    }
    public List<Future<Class>> getFutureData() {
        return futureData;
    }
    public Future<Class> createAndStartAsyncTask() {
        CompletableFuture<Class> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            try {
                Thread.sleep(500);
                // and do whatever you need to do or throw an exception in case something went wrong
                throw new InterruptedException(); // just the exceptional case here for demonstration purposes
                //return null; // return normally if there would be not exception
            } catch (Exception e) {
                completableFuture.completeExceptionally(e);
                // don't call  completableFuture.cancel(false )! that wouldn't work
                // don't call  completableFuture.cancel(true )! that wouldn't work either
            }
        });
        return completableFuture;
    }
    public Future<Class> createAndStartFutureTask() {
        FutureTask<Class> ftask = new FutureTask<Class>(() -> {
            Thread.sleep(500);
            throw new InterruptedException(); // just the exceptional case here for demonstration purposes
            //return MySOClass.class; // return normally if there would be not exception
        });
        ftask.run();
        return ftask;
    }
    public void collectAsyncResults() {
        ExampleProductionCode.EnginesData enginesData = this.getEngineData();
        futureData.forEach(result -> {
            try {
                enginesData.add(result.get()); // the method I am using to force an exception using Mockito
            } catch (InterruptedException | ExecutionException e) {
                // "the catch block I am trying to cover"
                // if you want to cover this catchblock you need to
                // 1. execute some method here, and then
                // 2. spy on this method, so you can check in you test, whether this method was really called with the correct parameters
                myService.logError(e);
            }
        });
    }
    public class EnginesData {
        public void add(Class aclass) {
            //do something
        }
    }
}
