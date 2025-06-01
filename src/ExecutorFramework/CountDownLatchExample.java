package ExecutorFramework;

import java.util.concurrent.*;

/**
 * <h3><b>CountDownLatch</b></h3>
 * <p>A CountDownLatch is a synchronization aid that allows one or more threads to wait until a set of operations being performed in other threads completes.
 * So for e.g., if we have 10 dependent service that needs to be started before we come to execution in main, then on using future.get,
 * we'd have to write future.get for each thread. But by using CountDownLatch we can send the numberOfServices/Threads in the params, and it has the ability to make the current
 * thread wait till operations on other thread is completed</p>
 * It uses the analogy of counter. So we initialize CountDownLatch with x number of processes. And as one-by-one the processes finish, it will reduce the counter value.
 * <p>Note: CountDownLatch is just for waiting current thread till the x number of processes finish. on using .await() with timeout, if time exceeds the amount defined,
 * it will resume the execution of the current thread. The other threads will still be running, i.e., it doesn't shut them down on timeout, it just resumes the current threads execution.</p>
 * CountDownLatch is non-reusable. Once used it cant be used again.
 * <ul>
 *     <li>new CountDownLatch(numberOfServices): Creates a countDownLatch with provided number of services</li>
 *     <li>countDownLatch.await(): Wait till the number of processes that were defined in the countDownLatch has been completed</li>
 *     <li>countDownLatch.await(timeout, timeUnit): Wait for "timeout" time for the number of processes that were defined in the countDownLatch to be completed. After the timeout it will resume the current thread, but the other threads continue execution in background.</li>
 *     <li>countDownLatch.countDown(): Reduces the count of countDownLatch</li>
 * </ul>
 */
public class CountDownLatchExample {
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
