package Locks;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class T5_LockFairnessExample {
    /**
     * <p>This is an unfair lock. An unfair lock does not guarantee any order — a thread may "barge in" and acquire the lock even if other threads have been waiting longer.</p>
     *
     * Allows higher throughput (because no strict ordering).
     * Threads that recently released the lock may re-acquire it faster.
     * May lead to thread starvation (especially if there are many competing threads).
     */
    private final Lock lock = new ReentrantLock();

    /**
     * <p>A fair lock ensures that the longest-waiting thread gets the lock first — basically, first-come, first-served order.</p>
     *
     * Threads acquire the lock in request order.
     * Reduces the chance of starvation (some thread waiting forever).
     * May be slightly slower due to the overhead of maintaining the queue.
     */
    private final Lock fairlock = new ReentrantLock(true);

    public void accessResource() {
        fairlock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " acquired the lock");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(Thread.currentThread().getName() + " released the lock");
            fairlock.unlock();
        }
    }

    public static void main(String[] args) {
        T5_LockFairnessExample resource = new T5_LockFairnessExample();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                resource.accessResource();
            }
        };
        ArrayList<Thread> threads = new ArrayList<>();
        for(int i=0;i<20;i++){
            threads.add(new Thread(task, "Thread " + (i+1)));
        }
        threads.forEach(Thread::start);
    }
}
