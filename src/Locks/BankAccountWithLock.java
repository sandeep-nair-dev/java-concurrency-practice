package Locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccountWithLock {
    int balance = 100;
    Lock lock = new ReentrantLock();

    public void withdraw(int amount) {
//        if(lock.tryLock()){     // Acquires the lock if it is available and returns immediately with the value true. If the lock is not available then this method will return immediately with the value false.
//
//        }

        try {
            // Waits till the lock is released(if some thread has acquired the lock) and then gets the lock. Similar to functioning of synchronized
//            lock.lock();

//            lock.lockInterruptibly();   // This will create an interruptible lock. so general locks will wait till they can acquire the lock but this lock can be interrupted. So if we declare this lock and in the main method we set that after 10sec interrupt the lock then in that case if the thread is waiting to acquire the lock and main method interrupts the thread, then it can be interrupted.
            if (lock.tryLock(5000, TimeUnit.MILLISECONDS)) {  //Acquires the lock if it is free within the given waiting time and the current thread has not been interrupted. Here it is waiting, and if during waiting the thread is interrupted, it throws interrupted exception.
                if (balance < amount) {
                    System.out.println(Thread.currentThread().getName() + "Insufficient balance");
                } else {
                    try {
                        System.out.println(Thread.currentThread().getName() + " proceeding with transaction ");
                        Thread.sleep(3000);
                        balance -= amount;
                        System.out.println(Thread.currentThread().getName() + " transaction completed. Remaining balance: " + balance);
                    } catch (Exception e) {
                        System.out.println("Interrupted Exception while processing transaction");
                        Thread.currentThread().interrupt();     // If this not done and simply exception is thrown then the info in the current interrupted thread could be lost. This marks the thread as interrupted and if anything is monitoring this thread, it informs them that the thread has been interrupted
                    } finally {
                        lock.unlock();      //unlocks the lock
                    }
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " could not acquire lock. Will try again later.");
                Thread.currentThread().interrupt();
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " Thread interrupted");
        }
    }
}

