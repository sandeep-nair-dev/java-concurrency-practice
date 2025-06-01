package Locks;

/*
    When multiple threads are working on the same object, they might access the same object and change it at the same time causing conflict. This is called race condition.
    e.g. value of counter was 600 and both t1 and t2 's increment method executed at the same time, so both incremented it from 600-> 601...whereas we expected it to be 602, hence sunch issues arise.
    The part of code that is accessible and modifiable by multiple threads are called critical section.
    We use synchronized to make sure only one thread access that part at a time, when one accesses and modifies, others wait for their turn. This is known as mutual exclusion

    There are mainly 2 types of lock: intrinsic and extrinsic
    Synchronous is part of intrinsic lock

    Cons: One issue that exists with synchronized is that when using synchronized, if one thread is accessing the data and takes long amount of time, then other threads are also stuck in waiting until this lock is released.
 */

class Counter {
    int count = 0;

    public synchronized void increment() {
        count++;

//        Synchronized block is used when you only want a part of code to be synchronized and not the whole function. "this" refers to the current instance on which operation is being performed.
//        synchronized (this){
//            count++;
//        }
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
