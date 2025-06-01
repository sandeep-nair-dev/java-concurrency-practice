package ExecutorFramework;

import java.util.concurrent.*;

/**
 * <h3><b>Completable Future</b></h3>
 * <p>A Future that may be explicitly completed (setting its value and status), and may be used as a CompletionStage, supporting dependent functions and actions that trigger upon its completion.</p>
 * <ul>
 *     <li>CompletableFuture.supplyAsync(Supplier<?>): Returns a new CompletableFuture that is asynchronously completed by a task running in the ForkJoinPool.commonPool()
 *     with the value obtained by calling the given Supplier. The function provided in supplier is run asynchronously and it doesn't block main thread.
 *     It is a daemon thread by default so the main thread doesn't wait for it to complete its execution.</li>
 *     <li>Completable Future with thread pool:
 *     <pre>{@code ExecutorService executorService  = Executors.newFixedThreadPool(3);
 *         CompletableFuture<String> completableFutureWithExecutorThreads = CompletableFuture.supplyAsync(() -> {})}</pre>
 *
 *         In this we provide executorService ThreadPool to Completable Future so it will use these threads for execution.
 *         And since it is using these threads from thread pool and these are not daemon threads, hence now completable future is also not daemon and main function waits for its
 *         execution to finish.
 *     </li>
 *     <li>CompletableFuture.get(): Blocks the main thread and makes it wait till the task given as supplier to completable future is completed.</li>
 *     <li>CompletableFuture.getNow(valueIfAbsent): Checks whether the completableFuture has completed execution or not.
 *     It is completed it returns the value that was returned by supplier (or throws unencountered exception) and if not completed it returns the "valueIfAbsent"</li>
 *     <li>.orTimeOut(time, unit): Waits for this much time for completion of CompletableFuture and if within this time if it doesn't finishes, returns exception.
 *     <li>.exceptionally(Function): If exception is returned, we can make changes to it and handle as we want here itself;
 *     <li>CompletableFuture.allOf(CompletableFuture<?>...): Returns a new CompletableFuture that is completed when all of the given CompletableFutures complete.
 *     If any of the given CompletableFutures complete exceptionally, then the returned CompletableFuture also does so, with a CompletionException holding this exception as its cause.
 *     Otherwise, the results, if any, of the given CompletableFutures are not reflected in the returned CompletableFuture,
 *     but may be obtained by inspecting them individually. If no CompletableFutures are provided, returns a CompletableFuture completed with the value null. </li>
 *     <li>CompletableFuture.join(): Similar to get() makes the current thread wait for the completable future to complete execution.
 *     It has exception mentioned in method signature whereas .get() doesn't</li>
 * </ul>
 */
public class CompletableFutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("Worker Thread");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "ok";
        }).orTimeout(10, TimeUnit.SECONDS).exceptionally((x)-> "Error: " + x);
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
        ExecutorService executorService  = Executors.newFixedThreadPool(3);
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
