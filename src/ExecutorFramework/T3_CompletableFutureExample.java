package ExecutorFramework;

import java.util.concurrent.*;

/**
 * <h3><b>CompletableFuture</b></h3>
 * <p>
 * A {@code Future} that may be explicitly completed (by setting its value and status), and may be used as a {@code CompletionStage},
 * supporting dependent functions and actions that trigger upon its completion.
 * </p>
 *
 * <ul>
 *     <li><b>{@code CompletableFuture.supplyAsync(Supplier<?>)}:</b><br>
 *         Returns a new {@code CompletableFuture} that is asynchronously completed by a task running in the {@code ForkJoinPool.commonPool()}.
 *         The supplier function runs asynchronously and does not block the main thread.<br>
 *         It uses a daemon thread by default, so the main thread does not wait for its execution to finish.
 *     </li>
 *
 *     <li><b>CompletableFuture with custom thread pool:</b>
 *         <pre>{@code
 * ExecutorService executorService = Executors.newFixedThreadPool(3);
 * CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
 *     // task logic
 * }, executorService);
 *         }</pre>
 *         In this case, a custom {@code executorService} is provided, so the task uses non-daemon threads.
 *         Therefore, the main thread will wait for the completion of the CompletableFuture.
 *     </li>
 *
 *     <li><b>{@code CompletableFuture.get()}:</b><br>
 *         Blocks the current thread and waits for the task (provided in the supplier) to complete.
 *     </li>
 *
 *     <li><b>{@code CompletableFuture.getNow(valueIfAbsent)}:</b><br>
 *         If the task is complete, it returns the result (or throws an encountered exception).
 *         If not completed, it returns the provided {@code valueIfAbsent}.
 *     </li>
 *
 *     <li><b>{@code .orTimeout(time, unit)}:</b><br>
 *         Waits for the specified time for the task to complete.
 *         If it doesn’t finish in time, it completes the future exceptionally.
 *     </li>
 *
 *     <li><b>{@code .exceptionally(Function)}:</b><br>
 *         Handles exceptions that occur during task execution. You can modify the response in this block.
 *     </li>
 *
 *     <li><b>{@code CompletableFuture.allOf(CompletableFuture<?>...)}:</b><br>
 *         Returns a new {@code CompletableFuture} that is completed when all given futures complete.
 *         <ul>
 *             <li>If any provided future completes exceptionally, the result will also complete exceptionally with a {@code CompletionException}.</li>
 *             <li>The returned future does not contain the results of the individual futures—you must inspect each one separately.</li>
 *             <li>If no futures are provided, the result is a {@code CompletableFuture} completed with {@code null}.</li>
 *         </ul>
 *     </li>
 *
 *     <li><b>{@code CompletableFuture.join()}:</b><br>
 *         Similar to {@code get()}, this method waits for the task to complete,
 *         but it throws an unchecked exception (wrapped in {@code CompletionException}),
 *         unlike {@code get()} which throws checked exceptions.
 *     </li>
 * </ul>
 */

public class T3_CompletableFutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("Worker Thread");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "ok";
        }).orTimeout(10, TimeUnit.SECONDS).exceptionally((x) -> "Error: " + x);
        System.out.println(completableFuture.getNow("Value absent so printing default"));
        System.out.println(completableFuture.get());
        System.out.println("Main Thread");
        System.out.println("----------------------------------------------------");

        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("Worker Thread");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "ok";
        });
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("Worker Thread");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "ok";
        });

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(completableFuture1, completableFuture2);
        voidCompletableFuture.join();
        System.out.println("Completed");

        System.out.println("-------------------------------");
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletableFuture<String> completableFutureWithExecutorThreads = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("Worker Thread");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "ok";
        }, executorService);

        System.out.println("completableFutureWithExecutorThreads completed");

        executorService.shutdown();
    }
}
