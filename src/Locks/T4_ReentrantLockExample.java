package Locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * If we use a normal lock and call {@code outerMethod()}, we first acquire the lock.
 * Inside {@code outerMethod()}, when we call {@code innerMethod()} and try to acquire the same lock again,
 * it leads to a problem: how can we acquire a lock that we already hold?
 * </p>
 *
 * <p>
 * The issue arises because {@code innerMethod()} waits for the lock to be released by {@code outerMethod()},
 * but {@code outerMethod()} can’t release the lock until {@code innerMethod()} completes — this results in a <b>deadlock</b>.
 * </p>
 *
 * <p>
 * Alternatively, if we let {@code innerMethod()} acquire the lock and release it in its {@code finally} block,
 * another thread might acquire the lock right after that release and enter the outer method,
 * even though there might be operations in {@code outerMethod()} after the call to {@code innerMethod()}.
 * </p>
 *
 * <p>
 * <b>Solution:</b> We use {@code ReentrantLock}, which allows the same thread to re-acquire the lock multiple times.
 * </p>
 *
 * <p>
 * {@code ReentrantLock} maintains a count of how many times the current thread has acquired the lock:
 * <ul>
 *     <li>In {@code outerMethod()}, the lock count becomes 1</li>
 *     <li>In {@code innerMethod()}, the lock count becomes 2</li>
 *     <li>When {@code innerMethod()} finishes, it releases the lock → count becomes 1</li>
 *     <li>When {@code outerMethod()} finishes, it releases the lock → count becomes 0</li>
 * </ul>
 * The lock is truly released only when the count reaches 0.
 * </p>
 */

public class T4_ReentrantLockExample {
    Lock lock = new ReentrantLock();
    public void outerMethod(){
        lock.lock();
        try {
            System.out.println("Outer Method");
            innerMethod();
        }finally {
            lock.unlock();
        }
    }

    public void innerMethod(){
        lock.lock();
        try {
            System.out.println("Inner method");
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        T4_ReentrantLockExample example = new T4_ReentrantLockExample();
        example.outerMethod();
    }
}
