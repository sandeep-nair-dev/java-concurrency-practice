package ExecutorFramework;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 *  <h3><b>InvokeAll and InvokeAny</b></h3>
 *  <ul>
 *      <li>invokeAll and invokeAny are blocking calls, i.e., main thread waits for the thread to complete its execution.</li>
 *      <li>It returns a List<Future> which contains list of futures i.e., result of the callables</li>
 *      <li><b>invokeAll(Collection<>)</b>: executes all the callables present in the list passed into it</li>
 *      <li><b>invokeAll(Collection<>, timeout, TimeUnits)</b>: executes all the callables it can with the threads available to it within the provided timeout.
 *      After timeout if something left, it throws CancellationException</li>
 *      <li><b>invokeAny(Collection<>)</b>: executes the callables and as soon as one is done and it gets the result it gets that and ignores others</li>
 *      <li><b>invokeAny(Collection<>, timeout, TimeUnits)</b>: Just like invokeAny, just within the provided timeout</li>
 *  </ul>
 */
public class InvokeAndInvokeAll {

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
