


class Hi1 implements Runnable {
    @Override
    public void run() {
        show();
    }

    public void show() {

        for (int i = 0; i < 5; i++) {
            System.out.println("Hi");
            try {
                Thread.sleep(500);
            } catch (Exception e) {
            }
        }
    }
}

class Hello1 implements Runnable {

    @Override
    public void run() {
        show();
    }

    public void show() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Hello");
            try {
                Thread.sleep(500);
            } catch (Exception e) {
            }
        }
    }
}

public class usingRunnableInterface {

    public static void main(String[] args) {
        Runnable obj1 = new Hi1();
        Runnable obj2 = new Hello1();

        Thread t1 = new Thread(obj1);
        Thread t2 = new Thread(obj2);

        t1.start();
        try {
            Thread.sleep(10);
        } catch (Exception e) {
        }
        t2.start();
    }


}
