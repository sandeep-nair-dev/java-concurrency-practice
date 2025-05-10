
class Hi extends Thread {
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

class Hello extends Thread {

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

public class UsingThreadClass {

    public static void main(String[] args) {
        Hi hi = new Hi();
        Hello hello = new Hello();
        hi.start();
        try {
            Thread.sleep(10);
        } catch (Exception e) {
        }
        hello.start();
    }
}
