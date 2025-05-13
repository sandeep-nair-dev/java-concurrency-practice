package Locks;

public class BankAccount {
    int balance =100;

    /*
        Here when we are using synchronized, no other thread can access the thread unless current thread releases the lock.
        So for eg if t1 has acquired the lock and now t2 comes and sees that t1 is using it, t2 is stuck here unless t1 releases the lock. t2 is unable to go back too.
        So to avoid this, we use Lock class to manually handle the locking and unlocking.
     */
    public synchronized void withdraw(int amount){
        System.out.println(Thread.currentThread().getName() + " attempting to withdraw " + amount);
        if(balance<amount){
            System.out.println(Thread.currentThread().getName()+"Insufficient balance");
        }else {
            System.out.println(Thread.currentThread().getName() + " proceeding with transaction ");
            try {
                Thread.sleep(10000);
            }catch (Exception e){
                System.out.println("Interrupted Exception");
            }
            balance-=amount;
            System.out.println(Thread.currentThread().getName() + " transaction completed. Remaining balance: " + balance);
        }
    }
}
