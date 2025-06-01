package ExecutorFramework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <h3><b>Executor Service</b></h3>
 * <p>Executor service is an implementation of Executor. It provides multiple methods to simplify creation and handling of threads</p>
 * <p>Some methods shown here are:
 * <ul>
 *     <li>Executors.newFixedThreadPool: Creates a new thread pool that we can use for processing. These threads can be reused.</li>
 *     <li>Executors.newSingleThreadExecutor: Creates an executor with single thread</li>
 *     <li>Executors.newCachedThreadPool(): Creates a thread pool that creates new threads as needed, but will reuse previously constructed threads when they are available.
 *     And when load decreases, they shutdown threads after 60s of inactivity. This one creates threads depending on how much is needed and hence can result in creation of large
 *     number of threads. That's why it is better to use this for short-lived asynchronous tasks.</li>
 *     <li>executorService.submit(Runnable runnable): It contains the process we want to do. It expects a runnable that will be the implementation,
 *     like we used to do when creating thread using runnable. It creates and starts the thread too. No need to explicitly call something like thread.start(). Returns Future<?></?></li>
 *     <li>executorService.shutdown(): Starts shutdown of threads and ThreadPool.
 *     After shutdown we cant use the executor service again. Shutdown occurs in background and main thread doesn't wait for shutdown to complete.
 *     If we want to wait till shutdown completes, we'll have to use executorService.awaitTermination()</li>
 *     <li> executorService.awaitTermination(timeout, unit): this will wait for timeout unit amount of times, if shutdown completed returns true,
 *     and if time elapsed before termination returns false. If we want to wait indefinitely we use !while(executorService.awaitTermination(timeout, unit))</li>
 *     <li>executorService.shutdownNow(): Shutdown the executor service then and there</li>
 *     <li>executorService.isTerminated():Returns true if all tasks have completed after shut down.</li>
 * </ul>
 * <p>
 * This functions same as the below code, just more manageable instead of manually creating threads
 * <pre>{@code Thread[] threads = new Thread[10];
 *         for (int i = 0; i < 10; i++) {
 *             int finalI = i;
 *             threads[i]=new Thread(()->{
 *                 System.out.println(factorial(finalI));
 *             });
 *             threads[i].start();
 *         }
 *
 *         for (Thread thread : threads){
 *             try {
 *                 thread.join();
 *             } catch (InterruptedException e) {
 *                 thread.interrupt();
 *             }
 *         }}</pre>
 * </p>
 */
public class ExecutorFramework {
    private static long factorial(int n) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long result = 1;
        for (int i = n; i > 0; i--) {
            result += i;
        }
        return result;
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 50; i++) {
            int finalI = i;
            executorService.submit(() -> {
                System.out.println(factorial(finalI));
            });
        }
        executorService.shutdown();
        System.out.println("Is executor service terminated: " + executorService.isTerminated());

        try {
//            executorService.awaitTermination(100, TimeUnit.SECONDS);      //Wait for 100 seconds, since we are sure our operation will be done within 100 seconds that's why we are waiting for 100 seconds
            while (!executorService.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                System.out.println("Waiting...");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Is executor service terminated: " + executorService.isTerminated());
        System.out.println("End time: " + (System.currentTimeMillis() - startTime));
    }

}
