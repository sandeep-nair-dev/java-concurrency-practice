package ExecutorFramework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <h3><b>Executor Service</b></h3>
 * <p>
 * {@code ExecutorService} is an implementation of the {@code Executor} interface. It provides various methods to simplify the creation,
 * management, and handling of threads and asynchronous task execution.
 * </p>
 *
 * <p><b>Common Methods:</b></p>
 * <ul>
 *     <li><b>{@code Executors.newFixedThreadPool(int n)}:</b>
 *         Creates a thread pool with a fixed number of reusable threads.
 *     </li>
 *     <li><b>{@code Executors.newSingleThreadExecutor()}:</b>
 *         Creates an executor that uses a single worker thread.
 *     </li>
 *     <li><b>{@code Executors.newCachedThreadPool()}:</b>
 *         Creates a thread pool that creates new threads as needed but will reuse previously constructed threads when available.
 *         Threads that remain idle for 60 seconds are terminated. Best suited for short-lived asynchronous tasks.
 *     </li>
 *     <li><b>{@code executorService.submit(Runnable runnable)}:</b>
 *         Submits a task for execution. It creates and starts the thread behind the scenes.
 *         No explicit {@code thread.start()} is needed. Returns a {@code Future<?>} object.
 *     </li>
 *     <li><b>{@code executorService.shutdown()}:</b>
 *         Initiates an orderly shutdown where previously submitted tasks are executed, but no new tasks will be accepted.
 *         Does not block the main thread. Use {@code awaitTermination()} to wait for shutdown completion.
 *     </li>
 *     <li><b>{@code executorService.awaitTermination(long timeout, TimeUnit unit)}:</b>
 *         Causes the current thread to wait for the termination of the executor for the given time.
 *         Returns {@code true} if terminated in time, {@code false} otherwise.
 *         For indefinite wait: <br>
 *         <pre>{@code
 * while (!executorService.awaitTermination(timeout, unit)) {
 *     // keep waiting
 * }}</pre>
 *     </li>
 *     <li><b>{@code executorService.shutdownNow()}:</b>
 *         Attempts to stop all actively executing tasks and halts the processing of waiting tasks.
 *     </li>
 *     <li><b>{@code executorService.isTerminated()}:</b>
 *         Returns {@code true} if all tasks have completed following shutdown.
 *     </li>
 * </ul>
 *
 * <p><b>Equivalent Manual Thread Example:</b></p>
 * <p>This example manually creates and manages 10 threads for computing factorials, similar to what an executor service simplifies:</p>
 *
 * <pre>{@code
 * Thread[] threads = new Thread[10];
 * for (int i = 0; i < 10; i++) {
 *     int finalI = i;
 *     threads[i] = new Thread(() -> {
 *         System.out.println(factorial(finalI));
 *     });
 *     threads[i].start();
 * }
 *
 * for (Thread thread : threads) {
 *     try {
 *         thread.join();
 *     } catch (InterruptedException e) {
 *         thread.interrupt();
 *     }
 * }
 * }</pre>
 */

public class T0_ExecutorFramework {
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
