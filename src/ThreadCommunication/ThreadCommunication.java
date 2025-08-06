package ThreadCommunication;

/**
 * wait(), notify, and notifyAll()
 * <ul>
 *     <li> wait() -> Releases the lock and waits till some other thread calls notify() or notifyAll() method</li>
 *     <li>notify() -> It is used to wake up a single thread that is waiting</li>
 *     <li>notifyAll() -> It is used to wake up multiple threads that are there in the waiting stage</li>
 * </ul>
 */
class SharedResource {
    private int data;
    private boolean hasData;

    public synchronized void produce(int value) {
        if (hasData) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        data = value;
        System.out.println("Produced: " + value);
        hasData = true;
        notify();
    }

    public synchronized void consume() {
        while (!hasData) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        hasData = false;
        System.out.println("Consumed: " + data);
        notify();
    }
}

class Producer implements Runnable {
    private final SharedResource resource;

    Producer(SharedResource sharedResource) {
        this.resource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            resource.produce(i);
        }
    }
}

class Consumer implements Runnable {
    private final SharedResource resource;

    Consumer(SharedResource sharedResource) {
        this.resource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            resource.consume();
        }
    }
}

public class ThreadCommunication {

    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();
        Thread producerThread = new Thread(new Producer(sharedResource));
        Thread consumerThread = new Thread(new Consumer(sharedResource));
        producerThread.start();
        consumerThread.start();
    }
}

