import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h3><b>Atomic</b></h3>
 * <p>Atomic Classes are used to ensure that during each operation atomicity is maintained, i.e., one operation being performed will be completed without interference from other threads.
 * It makes sure only one thread operates on the variable at a time.</p>
 */
public class AtomicExample {

    private final AtomicInteger counter=new AtomicInteger(0);

    public void increment(){
        counter.incrementAndGet();
    }

    public int getCounter(){
        return counter.get();
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicExample atomicExample = new AtomicExample();
        Thread t1 = new Thread(()->{
            for (int i=0;i<1000;i++){
                atomicExample.increment();
            }
        });

        Thread t2 = new Thread(()->{
            for (int i=0;i<1000;i++){
                atomicExample.increment();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Result: " + atomicExample.getCounter());
    }
}
