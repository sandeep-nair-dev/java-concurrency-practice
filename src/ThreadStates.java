public class ThreadStates extends Thread{
    @Override
    public void run() {
        System.out.println("Thread running");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new ThreadStates();
        System.out.println(t1.getState());      //NEW
        t1.start();
        System.out.println(t1.getState());      //RUNNABLE
        System.out.println(Thread.currentThread().getState());  //RUNNABLE.....In java, both runnable and running thread are in RUNNABLE state
        Thread.sleep(100);
        System.out.println(t1.getState());      //TIMED_WAITING
        t1.join();
        System.out.println(t1.getState());      //TERMINATED


    }
}
