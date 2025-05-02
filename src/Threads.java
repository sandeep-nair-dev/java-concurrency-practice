public class Threads {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()-> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Hi");
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
            }
        });
        Thread t2 = new Thread(()->{
            for (int i = 0; i < 5; i++) {
                System.out.println("Hello");
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
            }
        });

        t1.start();
        try {
            Thread.sleep(10);
        } catch (Exception e) {
        }
        t2.start();

        System.out.println("Main Thread");      //This is printed between hi and hello because hi and hello are being printed by different threads and this is being handle by main thread, so it stated t1 and t2 and then continued the main thread execution

        //isAlive checks if the thread is currently executing or not
        System.out.println("t1 alive?: " + t1.isAlive());

        // join() will wait for the mentioned threads to complete their execution
        t1.join();
        t2.join();
        System.out.println("t1 alive?: " + t1.isAlive());

        System.out.println("Main thread after join()");

    }
}
