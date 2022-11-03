package de.playground.so74236327;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.mockito.Mockito.*;
// see https://stackoverflow.com/questions/74236327/why-cant-i-throw-an-exception-when-a-future-mocked-objects-method-is-executed
@ExtendWith(MockitoExtension.class) // needed for the @Mock annotation
public class FutureExceptionTest {
    @Mock
    public Future<Class> futureMock;
    @Test
    public void testInterruptedException() throws ExecutionException, InterruptedException {
        MyService mySpy = spy(new MyService());
        ExampleProductionCode exampleProductionCode = new ExampleProductionCode(mySpy);// contains 2 real Future<Class> objects.
        List<Future<Class>> futureData = exampleProductionCode.getFutureData();
        InterruptedException interruptedException = new InterruptedException("Interrupted Exception");
       // Future<Class> oneFutureData = Mockito.mock(Future.class);
        //when(oneFutureData.get()).thenThrow(CompletableFuture.completedFuture(interruptedException)); that doesn't even compile
        when(futureMock.get()).thenThrow(interruptedException);
        futureData.add(futureMock); // add another Future<Class> object, but this time it's just a mock object
        verify(mySpy, times(0)).logError(interruptedException);//nothing has happend yet
        exampleProductionCode.collectAsyncResults(); // calling the 3 Future.get() methods inside
        // 3 cases:
        // 1st: with the completableFuture.completeExceptionally(e);
        // 2nd: FutureTask<Class> throwing an exception inside the task
        // 3rd: comes from the  oneFutureData mock object above
        verify(mySpy, times(3)).logError(any(Exception.class));
    }
}
