package Locks;


/**
 * <p>In this Paper.writeWithPaperAndPen() is trying to access Pen.finishWriting(), and Pen.writeWithPenAndPaper() is trying to access Paper.finishWriting()....but since both are depending on each other to release lock so that they can acquire lock. This results in a deadlock
 * <p>This is resolved by ensuring that multiple threads follow a set order of acquiring locks. Like in this example it is resolved by using synchronized block with condition in task2. So this ensures that before paper.writeWithPaperAndPen(pen); executes Pen is not locked, and it can access Pen. Since task2 will wait till Pen is unlocked, task1 is able to proceed with its operation and on its end it releases lock and now task2 is able to access the lock hence, it executes.
 */
public class DeadLockExample {
    static class Paper {
        public synchronized void writeWithPaperAndPen(Pen pen) {
            System.out.println(Thread.currentThread().getName() + " is using paper " + this + " and trying to use pen " + pen);
            pen.finishWriting();
        }

        public synchronized void finishWriting() {
            System.out.println(Thread.currentThread().getName() + " finished using paper " + this);
        }
    }

    static class Pen {
        public synchronized void writeWithPenAndPaper(Paper paper) {
            System.out.println(Thread.currentThread().getName() + " is using pen " + this + " and trying to use paper " + paper);
            paper.finishWriting();
        }

        public synchronized void finishWriting() {
            System.out.println(Thread.currentThread().getName() + " finished using pen " + this);
        }
    }

    public static void main(String[] args) {
        Paper paper = new Paper();
        Pen pen = new Pen();

        Runnable task1 = () -> {
            pen.writeWithPenAndPaper(paper);
        };
        Runnable task2 = () -> {
            synchronized (pen) {                // this ensures that task2 is executed only when lock of pen is not acquired and it can acquire the lock of pen.
                paper.writeWithPaperAndPen(pen);
            }
        };

        Thread t1 = new Thread(task1, "Thread-1");
        Thread t2 = new Thread(task2, "Thread-2");

        t1.start();
        t2.start();

    }
}
