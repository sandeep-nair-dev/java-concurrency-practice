package Locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * In case normal lock was there, firstly we go to outerMethod() and acquire the lock.
 * After that we go to innerMethod() and there also we are acquiring the lock. How can we acquire the lock that we had already acquired.
 * Inner method depends on lock to be released by outerMethod() and to release lock by outerMethod, we need the innerMethod() to be completed. This results in deadlock
    Similarly lets say if we just keep lock acquired in innerMethod() and proceed, then when we go to finally block of innerMethod() it would unlock the lock and if
 some other thread was waiting it would be able to access the outer method whereas there could have been some operations to be performed after the innerMethod() call



    Hence, we use ReentrantLock. Reentrant lock allows re-acquire of thread since same thread is acquiring it.
 Reentrant Lock keeps a count of the no. of times lock was acquired. So in outer method lock count=1, int innerMethod() lock count=2, in finally of innerMethod()
 lock released so lock count=1 and in finally of outerMethod() lock released so lock count=0. When lock count=0 that is truly when the lock is released.
 */
public class ReentrantLockExample {
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
        ReentrantLockExample example = new ReentrantLockExample();
        example.outerMethod();
    }
}
