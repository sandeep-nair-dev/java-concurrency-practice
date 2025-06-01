package ExecutorFramework;

import java.util.concurrent.*;

/**
 * <h3><b>Cyclic Barrier</b></h3>
 * <p>A cyclic Barrier creates a kind of barrier and trips when the given number of parties (threads) are waiting upon it.
 * So if we mention 4 as number of parties, then for each task, the thread will perform the task and wait at cyclicBarrier.await() till all the parties mentioned have reached the point</p>
 * <p>It blocks the threads or parties that have cyclic barrier in them but it doesn't block the main thread.</p>
 * <ul>
 *     <li>CyclicBarrier(int parties): Creates a cyclic barrier with provided number of parties</li>
 *     <li>CyclicBarrier(int parties, Runnable barrierAction): Creates a cyclic barrier with provided number of parties. It will execute the barrier action when barrier is tripped, that is all parties reach the await code line. The barrier action is performed by the last thread that enters the barrier.</li>
 *     <li>cyclicBarrier.await(): Waits until all parties have invoked await on the barrier. Waits till the last thread invokes await or one of the parties thread was interrupted or timeout while waiting or the cyclic barrier was reset.</li>
 *     <li>cyclicBarrier.reset(): Resets the barrier to its initial state. If any parties are currently waiting at the barrier(reached the await() code), they will return with a BrokenBarrierException</li>
 * </ul>
 */
public class CyclicBarrierExample {

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
