package ExecutorFramework;

import java.util.concurrent.*;

/**
 *  <b>executor.submit</b>
 *  <ul>
 *      <li>executor.submit returns Future<?> where ? denotes any data type applicable. Future can be used to check whether the task was completed or not and to wait for the thread to complete.</li>
 *      <li>Future.get() -> Waits till the thread has completed its execution to get the future which might contain any value returned, thread task completion status etc. It is a blocking call</li>
 *      <li>Future.get(timeout, TimeUnit) -> Wait till the timeout for thread execution. If execution doesn't finish by that time, it throws TimeoutException.</li>
 *      <li>Future.isDone() -> Checks whether the thread has completed execution (Completion may be due to normal termination, an exception, or cancellation)</li>
 *      <li>Future.cancel(boolean) -> It cancels the thread. It takes a boolean value<ul>
 *          <li> true: It will cancel the execution of the task whether it has started running or not.</li>
 *          <li> false: if the task is not running it will cancel the task. If the task has started running, it will mark the future as cancelled, but will allow the task to run and complete.</li>
 *      </ul></li>
 *      <li>Future.isCancelled() -> Returns boolean whether the task is cancelled or not.</li>
 *      <li> There are 2 implementations of executor.submit()
 *      <ol>
 *          <li>Future<T> submit(Callable<T> task) -> This is used when the function or task we defined for the thread/executor is returning some value. The Future's get method will return null upon successful completion.</li>
 *          <li>Future<T> submit(Runnable task) -> This is used when the function we defined for the thread/executor is not returning anything. The Future's get method will return the given result upon successful completion.</li>
 *          <li>Future<T> submit(Runnable task, T result) -> </li>
 *      </ol>
 *      </li>
 *  </ul>
 *
 *  <br><br>
 *  <b>Difference between Runnable and Callable</b>
 *  <ul>
 *      <li>Runnable doesn't return anything but Callable returns something</li>
 *      <li>Runnable and Callable, both are functional interfaces. Functional method of Runnable is run() whereas of callable is call()</li>
 *      <li>Runnable's run() doesn't have throws exception in the methods signature so we have to wrap code that may produce error in try catch. Callable's call method has throws exception in the method signature so in case of exception it will throw the error. Try catch not necessary
 *      <pre>{@code Runnable runnable1 = new Runnable() {
 *             @Override
 *             public void run() {
 *             //try catch necessary since method signature doesn't have throws exception
 *                 try {
 *                     Thread.sleep(1000);
 *                 } catch (InterruptedException e) {
 *                     throw new RuntimeException(e);
 *                 }
 *             }
 *         };
 *
 *         Callable callable1 = new Callable() {
 *         //try catch not necessary since method signature have throws exception
 *             @Override
 *             public Object call() throws Exception {
 *                 Thread.sleep(1000);
 *                 return 1;
 *             }
 *         };}</pre>
 *      </li>
 *
 *  </ul>
 */
public class FutureExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> "Submit with callable");
        Future<?> future2 = executorService.submit(() -> System.out.println("Submit with Runnable"));
        Future<String> future3 = executorService.submit(() -> System.out.println("Submit with Runnable and task to return"), "Returning this");
        System.out.println(future.get());
        System.out.println(future2.get());
        System.out.println(future3.get());
        if (future.isDone()) {
            System.out.println("Task 1 done!!!");
        }
        if (future2.isDone()) {
            System.out.println("Task 2 done!!!");
        }if (future3.isDone()) {
            System.out.println("Task 3 done!!!");
        }

        Future<String> future4 = executorService.submit(()-> {
            Thread.sleep(2000);
            System.out.println("Task 4 running...");
            return "Cancelling";
        });
        Thread.sleep(1000);
//        future4.cancel(true);
        future4.cancel(false);
        System.out.println(future4.isDone());
        System.out.println(future4.isCancelled());
        executorService.shutdown();


        Callable<String> callable = ()-> "Hello";
        Runnable runnable= ()-> System.out.println("Hello");
    }
}
