package Locks;


/**
 * <p>
 * In this example, {@code Paper.writeWithPaperAndPen()} tries to access {@code Pen.finishWriting()},
 * and {@code Pen.writeWithPenAndPaper()} tries to access {@code Paper.finishWriting()}.
 * <br>
 * Since both threads are waiting for each other to release the lock so they can proceed,
 * a circular wait occurs — resulting in a <b>deadlock</b>.
 * </p>
 *
 * <p>
 * This issue is resolved by ensuring that multiple threads follow a consistent order of acquiring locks.
 * In this example, the solution involves using a synchronized block with a condition in {@code task2}.
 * </p>
 *
 * <p>
 * This ensures that before {@code paper.writeWithPaperAndPen(pen);} is executed,
 * the {@code Pen} instance is not locked and can be accessed.
 * <br>
 * Since {@code task2} waits until {@code Pen} is unlocked, {@code task1} is able to proceed and eventually release its lock.
 * Then, {@code task2} can acquire the necessary lock and continue, thus preventing deadlock.
 * </p>
 */
public class T1_DeadLockExample {
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
