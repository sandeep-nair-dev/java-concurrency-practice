package ExecutorFramework;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <h3><b>Scheduled Executor Service</b></h3>
 * <p>
 * {@code ScheduledExecutorService} is an extension of {@code ExecutorService} that can schedule tasks
 * to run after a specified delay or periodically.
 * </p>
 *
 * <ul>
 *     <li>
 *         <b>{@code schedule(Runnable/Callable, delay, timeUnit)}</b>:<br>
 *         Schedules the task to execute once after the specified delay.
 *     </li>
 *
 *     <li>
 *         <b>{@code scheduleAtFixedRate(Runnable, initialDelay, period, timeUnit)}</b>:<br>
 *         Schedules a repeating task that begins after the {@code initialDelay}, and then runs at a fixed {@code period} rate.<br>
 *         <b>Note:</b> The time between consecutive executions is fixed regardless of task execution time.
 *         If the task takes longer than the period, subsequent executions may run concurrently or be delayed depending on the implementation.
 *     </li>
 *
 *     <li>
 *         <b>{@code scheduleWithFixedDelay(Runnable, initialDelay, delay, timeUnit)}</b>:<br>
 *         Schedules a repeating task that begins after the {@code initialDelay}, and then runs with a fixed {@code delay}
 *         between the <i>end</i> of one execution and the <i>start</i> of the next.
 *         This takes task duration into account and prevents overlap.
 *     </li>
 * </ul>
 */

public class T6_ScheduledExecutorServiceExample {
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
