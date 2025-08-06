package ThreadMethods;

class NewThread extends Thread{
    @Override
    public void run() {
        for (int i=0;i<5;i++){
            System.out.println(currentThread().getName() + " is running");

            // yield- A hint to the scheduler that the current thread is willing to yield its current use of a processor. The scheduler is free to ignore this hint.
            Thread.yield();
        }
    }
}
public class ThreadMethods extends Thread{

    ThreadMethods(String name){
        super(name);
    }

    @Override
    public void run() {
        for(int i=0;i<5;i++){
            String a = "";
            for(int j=0;j<10000;j++) {
                a += "a";
            }
            System.out.println(Thread.currentThread().getName() + " - Priority: " + Thread.currentThread().getPriority() + " - Count: " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Sleep interrupted. " + e);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new ThreadMethods("Thread 1");
        t1.setName("New name");
        t1.start();
        t1.join();

        System.out.println("------------------------------------");
        Thread l = new ThreadMethods("Low Priority Thread");
        Thread m = new ThreadMethods("Medium Priority Thread");
        Thread h = new ThreadMethods("High Priority Thread");

        // Hints the JVM about the priority of each thread. Not necessary that they will run in order. If two threads are available for execution, then the thread with more priority is allowed to execute
        l.setPriority(Thread.MIN_PRIORITY);
        m.setPriority(Thread.NORM_PRIORITY);
        h.setPriority(Thread.MAX_PRIORITY);

        l.start();
        m.start();
        h.start();

        l.join();
        m.join();
        h.join();
        System.out.println("------------------------------------");
        Thread t2 = new ThreadMethods("Interrupt thread");
        t2.start();
        t2.interrupt();     // interrupts the thread, if sleeping or anything it interrupts the thread. Interruption doesnt mean to kill the thread, it just sets the interrupt variable value in Thread as true. If the interrupt is ignored, it continues execution.
        t2.join();
        Thread.sleep(2000);
        System.out.println("-------------------------------------");
        Thread t3 = new NewThread();
        t3.setName("Thread 1");

        Thread t4 = new NewThread();
        t4.setName("Thread 2");

        t3.start();
        t4.start();
    }
}
