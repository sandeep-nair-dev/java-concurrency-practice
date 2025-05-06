package ThreadMethods;

public class ThreadMethods2 extends Thread{
    @Override
    public void run() {
        for (int i=0;i<5;i++){
            System.out.println(currentThread().getName() + " is running");

            // yield- A hint to the scheduler that the current thread is willing to yield its current use of a processor. The scheduler is free to ignore this hint.
            Thread.yield();
        }
    }

    public static void main(String[] args) {
        Thread t3 = new ThreadMethods2();
        t3.setName("Thread 1");

        Thread t4 = new ThreadMethods2();
        t4.setName("Thread 2");

        t3.start();
        t4.start();
    }
}
