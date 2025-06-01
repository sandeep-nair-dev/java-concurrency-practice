package ExecutorFramework;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *  <b>Scheduled Executor Service</b>
 *  <p>An ExecutorService that can schedule commands to run after a given delay, or to execute periodically.</p>
 *  <ul>
 *      <li><b>scheduledExecutorService.schedule(Runnable/Callable, delay, timeUnit)</b>: Schedule the task to be done after delay of "delay" amount of time.</li>
 *      <li><b>scheduledExecutorService.scheduleAtFixedRate(Runnable/Callable, initialDelay, period, timeUnit)</b>: Schedule a task to be done periodically starting after "initialDelay" every "period" amount of time. This doesnt take into consideration the time taken by task to complete and starts next task after set period.</li>
 *      <li><b>scheduledExecutorService.scheduleWithFixedDelay(Runnable/Callable, initialDelay, delay, timeUnit)</b>: Schedule a task to be done periodically starting after "initialDelay" every "delay" amount of time after task completion. This takes into consideration the time taken by the task and starts the next task only after the set delay after current task execution.</li>
 *  </ul>
 */
public class ScheduledExecutorServiceExample {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.schedule(() -> System.out.println("Running after delay of 5 seconds"), 5, TimeUnit.SECONDS);

        scheduledExecutorService.scheduleAtFixedRate(() -> System.out.println("Running every 5 seconds"), 5, 5, TimeUnit.SECONDS);
//        scheduledExecutorService.shutdown();          // this causes issues because when we immediately shut down after scheduleAtFixedRate, it may not even get the time to get to queue, hence won't execute. That's why we are using scheduler to shut down after 20 secs.

        scheduledExecutorService.scheduleWithFixedDelay(() -> System.out.println("Running after delay of 5 seconds after each task"), 5, 5, TimeUnit.SECONDS);
        scheduledExecutorService.schedule(() -> {
            System.out.println("Initiating shutdown");
            scheduledExecutorService.shutdown();
        }, 20, TimeUnit.SECONDS);
    }
}
