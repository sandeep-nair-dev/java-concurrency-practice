package Locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * In normal locks, till the time current threads release the lock, other threads cannot access the resource. But multiple threads should be allowed to read the resource concurrently since they are not modifying the data.
 * <p>
 * Hence, we are using ReadWriteLock. They have 2 locks, readLock and writeLock.
 * <ul>
 *  <li>ReadLock: It allows multiple threads to acquire the lock and access the resource simultaneously, provided the fact that write lock is not acquired.
 *  It allows multiple threads to read the resource</li>
 *  <li>WriteLock: It is used when we are modifying the data.
 *  If a thread acquires a write lock, other threads can't acquire read or write locks until current thread releases the write lock.
 *  It is mutually exclusive lock for write operations.</li>
 * </ul>
 */
public class T6_ReadWriteLockExample {
    int count = 0;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public void increment() {
        writeLock.lock();
        try {
            count++;
            Thread.sleep(50);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        } finally {
            writeLock.unlock();
        }
    }

    public int getCount() {
        readLock.lock();
        try {
            return count;
        } finally {
            readLock.unlock();
        }
    }

    public static void main(String[] args) {
        T6_ReadWriteLockExample readWriteLockExample = new T6_ReadWriteLockExample();
        Runnable writeTask = () -> {
            for (int i = 0; i < 10; i++) {
                readWriteLockExample.increment();
                System.out.println(Thread.currentThread().getName() + " incremented count.");
            }
        };

        Runnable readTask = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " read count: " + readWriteLockExample.getCount());
            }
        };
        Thread tw = new Thread(writeTask, "Writing thread");
        Thread tr1 = new Thread(readTask, "Reading thread 1");
        Thread tr2 = new Thread(readTask, "Reading thread 2");

        tw.start();
        tr1.start();
        tr2.start();
    }

}
