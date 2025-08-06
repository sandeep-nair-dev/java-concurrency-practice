package ExecutorFramework;

import java.util.concurrent.*;

/**
 * <h3><b>CountDownLatch</b></h3>
 * <p>
 * A {@code CountDownLatch} is a synchronization aid that allows one or more threads to wait
 * until a set of operations being performed in other threads completes.
 * </p>
 *
 * <p>
 * For example, if we have 10 dependent services that need to be initialized before proceeding in the main thread,
 * we could call {@code future.get()} for each thread individually. But with {@code CountDownLatch}, we simply initialize it with the number of services,
 * and it will cause the current (main) thread to wait until all other threads complete their execution.
 * </p>
 *
 * <p>
 * Internally, it uses a counter initialized with the number of tasks. Each time a task finishes, it calls {@code countDown()},
 * reducing the counter by 1. When the counter reaches 0, the main thread resumes execution.
 * </p>
 *
 * <p><b>Note:</b> {@code CountDownLatch} is for one-time use only — it cannot be reused after the count reaches zero.</p>
 *
 * <p><b>Timeout Behavior:</b> If you use {@code await(timeout, unit)} and the timeout expires before the latch count reaches zero,
 * the waiting thread resumes execution, but the worker threads continue running in the background.
 * It does not shut them down.
 * </p>
 *
 * <ul>
 *     <li><b>{@code new CountDownLatch(numberOfServices)}:</b> Creates a latch with the specified count.</li>
 *     <li><b>{@code countDownLatch.await()}:</b> Causes the current thread to wait until the latch has counted down to zero.</li>
 *     <li><b>{@code countDownLatch.await(timeout, unit)}:</b> Causes the current thread to wait for the specified time for the latch to reach zero.
 *         If timeout elapses first, it proceeds regardless.</li>
 *     <li><b>{@code countDownLatch.countDown()}:</b> Decrements the latch count by one.</li>
 * </ul>
 */

public class T4_CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        int numberOfServices = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfServices);
        CountDownLatch countDownLatch = new CountDownLatch(numberOfServices);
        Future<String> future1 = executorService.submit(new DependentService(countDownLatch));
        Future<String> future2 = executorService.submit(new DependentService(countDownLatch));
        Future<String> future3 = executorService.submit(new DependentService(countDownLatch));

//        countDownLatch.await();
        countDownLatch.await(3, TimeUnit.SECONDS);
        System.out.println("Main");
        executorService.shutdown();
    }
}

class DependentService implements Callable<String> {
    private final CountDownLatch countDownLatch;

    DependentService(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }


    @Override
    public String call() throws Exception {
        {
            try {
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName() + " service started.");
            } catch (Exception ignored) {

            } finally {
                countDownLatch.countDown();
            }
        }
        return "ok";
    }
}
