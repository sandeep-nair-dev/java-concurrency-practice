package ThreadMethods;

public class ThreadMethods3 extends Thread{
    @Override
    public void run() {
        while (true){
            System.out.println(currentThread().getName() + " is running");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /*
            - User Threads are main threads that is responsible for executing business logic. JVM waits for user threads to be completed to finish execution
            - Daemon Threads are background/helper threads that work in background. Like garbage collector. JVM doesn't wait for daemon threads to finish execution.
                As soon as main method and all user threads execution are completed, it terminates the daemon thread and finishes execution of the program.
         */

        Thread t1 = new ThreadMethods3();
        t1.setName("User Thread");
//        t1.start();
        Thread t2 = new ThreadMethods3();
        t2.setName("Daemon Thread");
        t2.setDaemon(true);
        t2.start();
        Thread.sleep(10);
        System.out.println("Main Method done!");

    }
}
