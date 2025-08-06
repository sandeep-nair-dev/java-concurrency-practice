package Locks;

/**
 * When multiple threads work on the same object, they might access and modify it at the same time, causing a conflict.
 * This is known as a <b>race condition</b>.
 * <p>
 * <b>Example:</b><br>
 * If the value of a counter is {@code 600} and both {@code t1} and {@code t2} execute the increment method at the same time,
 * both may read {@code 600} and increment to {@code 601}, resulting in an incorrect value.
 * The expected result was {@code 602}. This kind of issue arises due to concurrent access.
 * <p>
 * The section of code that can be accessed and modified by multiple threads simultaneously is called the <b>critical section</b>.
 * <br>
 * We use the {@code synchronized} keyword to ensure that only one thread accesses that part at a time.
 * When one thread accesses and modifies the data, the others wait for their turn.
 * This concept is known as <b>mutual exclusion</b>.
 * <p>
 * There are mainly two types of locks: <b>intrinsic</b> and <b>extrinsic</b>.<br>
 * {@code synchronized} uses the <b>intrinsic lock</b>.
 * <p>
 * <b>Cons:</b><br>
 * One drawback of using {@code synchronized} is that if one thread holds the lock and takes a long time to execute,
 * other threads are blocked and must wait until the lock is released.
 */


class Counter {
    int count = 0;

    /**
     * <b>Synchronized Block</b><br>
     * A synchronized block is used when you want only a <i>specific part</i> of the code to be synchronized
     * instead of the entire method.
     * <p>
     * {@code this} refers to the current instance of the object on which the operation is being performed.
     * <p>
     * <b>Example:</b><br>
     * To increment a shared counter safely within a synchronized block:
     * <pre>
     * synchronized (this) {
     *     count++;
     * }
     * </pre>
     */
    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

class MyThread extends Thread {
    private Counter counter;

    public MyThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            counter.increment();
        }
    }
}

public class Synchronized {

    public static void main(String[] args) {
        Counter counter = new Counter();
        MyThread t1 = new MyThread(counter);
        MyThread t2 = new MyThread(counter);
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Count: " + counter.getCount());
    }
}
