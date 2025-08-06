package ExecutorFramework;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * <h3><b>invokeAll and invokeAny</b></h3>
 * <p>
 * These are blocking methods provided by {@code ExecutorService} to execute multiple {@code Callable} tasks concurrently.
 * Both methods pause the main thread until results are available (or timeout occurs).
 * </p>
 *
 * <ul>
 *     <li>
 *         <b>{@code invokeAll(Collection<Callable<?>> tasks)}</b>:<br>
 *         Submits all tasks and waits for <i>all</i> of them to complete. Returns a {@code List<Future<?>>} containing the results of each task.
 *     </li>
 *
 *     <li>
 *         <b>{@code invokeAll(Collection<Callable<?>> tasks, long timeout, TimeUnit unit)}</b>:<br>
 *         Submits all tasks and waits up to the given timeout for them to complete.
 *         Any task not completed by the deadline is cancelled. You may receive {@code CancellationException} when accessing those.
 *     </li>
 *
 *     <li>
 *         <b>{@code invokeAny(Collection<Callable<?>> tasks)}</b>:<br>
 *         Executes the given tasks and returns the result of <i>one</i> that completes successfully (fastest).
 *         All other unfinished tasks are cancelled. Still blocks until at least one task finishes.
 *     </li>
 *
 *     <li>
 *         <b>{@code invokeAny(Collection<Callable<?>> tasks, long timeout, TimeUnit unit)}</b>:<br>
 *         Similar to {@code invokeAny}, but waits only for the specified timeout. If no task completes in time, throws {@code TimeoutException}.
 *     </li>
 *
 *     <li>
 *         <b>Note:</b> Both methods block the main thread until they return (i.e., they are synchronous).
 *     </li>
 * </ul>
 */

public class T1_InvokeAndInvokeAll {

    public static void main(String[] args) throws InterruptedException {
        List<Callable<Integer>> callables = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            int finalI = i;
            callables.add(()-> {
                Thread.sleep(1000);
                System.out.println("Task "+ finalI);
                return finalI;
            });
        }

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        System.out.println("----------Invoke All Example");
        try {
            List<Future<Integer>> futures = executorService.invokeAll(callables);       //blcoking
            for(Future<Integer> future:futures){
                System.out.println(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
        }

        System.out.println("------------------------------------------------------");
        System.out.println("----------Invoke All With Timeout");
        try {
            List<Future<Integer>> futures = executorService.invokeAll(callables, 1, TimeUnit.SECONDS);
            for(Future<Integer> future:futures){
                System.out.println(future.get());
            }
        } catch (InterruptedException | CancellationException |ExecutionException e) {
        }

        System.out.println("------------------------------------------------------");
        System.out.println("----------Invoke Any Example");
        try {
            Integer i = executorService.invokeAny(callables);
            System.out.println(i);
        } catch (InterruptedException | ExecutionException e) {
        }

        executorService.shutdown();


    }
}
