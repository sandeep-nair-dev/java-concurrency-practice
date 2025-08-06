package ExecutorFramework;

import java.util.concurrent.*;

/**
 * <h3><b>CyclicBarrier</b></h3>
 * <p>
 * A {@code CyclicBarrier} creates a synchronization point (a barrier) that blocks a set number of threads (parties)
 * until all have reached the barrier. Once all threads call {@code await()}, the barrier is tripped and all are released.
 * </p>
 *
 * <p>
 * For example, if 4 parties are specified, each thread will perform its assigned task and then call {@code cyclicBarrier.await()}.
 * It will wait there until all 4 threads reach that point. Only then will they all proceed.
 * </p>
 *
 * <p>
 * <b>Note:</b> {@code CyclicBarrier} only blocks the threads participating in it. It does <i>not</i> block the main thread unless it's one of the parties.
 * </p>
 *
 * <ul>
 *     <li><b>{@code CyclicBarrier(int parties)}:</b>
 *         Creates a barrier that will trip when the specified number of threads (parties) have called {@code await()}.
 *     </li>
 *
 *     <li><b>{@code CyclicBarrier(int parties, Runnable barrierAction)}:</b><br>
 *         Similar to the above, but allows specifying a {@code Runnable} that will run once, when the barrier is tripped.
 *         This action is executed by the <i>last thread</i> to reach the barrier.
 *     </li>
 *
 *     <li><b>{@code cyclicBarrier.await()}:</b>
 *         Causes the current thread to wait until all parties have invoked {@code await()} on this barrier.
 *         It may return early if:
 *         <ul>
 *             <li>The waiting thread is interrupted</li>
 *             <li>A timeout occurs</li>
 *             <li>The barrier is reset or broken</li>
 *         </ul>
 *     </li>
 *
 *     <li><b>{@code cyclicBarrier.reset()}:</b>
 *         Resets the barrier to its initial state.
 *         Any threads currently waiting at the barrier will receive a {@code BrokenBarrierException}.
 *     </li>
 * </ul>
 */

public class T5_CyclicBarrierExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        int numberOfSubSystems = 4;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(numberOfSubSystems, () -> System.out.println("All subsystems are up and running. System startup complete."));
//        ExecutorService executorService = Executors.newSingleThreadExecutor();        // This wont work because in this case there is only 1 thread and it will do the first task(here creating Web Server subsystem) and then wait at the barrier.await()...so other tasks cant be performed since the thread is already running and occupied. We'd need at least as many threads as many is the count going in barrier.
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfSubSystems);
        Future<?> webServerThread = executorService.submit(new Subsystem("Web server", 2000, cyclicBarrier));
        Future<?> databaseThread = executorService.submit(new Subsystem("Database", 4000, cyclicBarrier));
        Future<?> cacheThread = executorService.submit(new Subsystem("Cache", 4000, cyclicBarrier));
        Future<?> messagingServiceThread = executorService.submit(new Subsystem("Messaging Service", 4000, cyclicBarrier));


        System.out.println("Main Thread Running");
        Thread.sleep(3000);
        cyclicBarrier.reset();


        try {
            webServerThread.get();
            databaseThread.get();
            cacheThread.get();
            messagingServiceThread.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(5000);
        executorService.shutdown();
        System.out.println("Program completed successfully.");
    }


}

class Subsystem implements Runnable {

    private final String serviceName;
    private final int initializationTime;
    private final CyclicBarrier cyclicBarrier;

    public Subsystem(String serviceName, int initializationTime, CyclicBarrier cyclicBarrier) {
        this.serviceName = serviceName;
        this.initializationTime = initializationTime;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            System.out.println(serviceName + " initialization started");
            Thread.sleep(initializationTime);
            System.out.println(serviceName + " initialized successfully.");
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            System.out.println(serviceName + " failed at barrier: " + e);
            throw new RuntimeException(e);
        }
    }
}
