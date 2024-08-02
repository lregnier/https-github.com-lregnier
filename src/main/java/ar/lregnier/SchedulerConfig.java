package ar.lregnier;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class SchedulerConfig {

  private static final int API_THREAD_POOL_SIZE = 10;
  private static final int SERVICE_THREAD_POOL_SIZE = 5;
  private static final int TEST_THREAD_POOL_SIZE = 5;

  private static final String API_THREAD_PREFIX = "api-thread";
  private static final String SERVICE_THREAD_PREFIX = "service-thread";
  private static final String TEST_THREAD_PREFIX = "TEST-thread";

  public static final Scheduler API_SCHEDULER = Schedulers.fromExecutorService(
      Executors.newFixedThreadPool(API_THREAD_POOL_SIZE,
          newNamedThreadFactory(API_THREAD_PREFIX))
  );

  public static final Scheduler SERVICE_SCHEDULER = Schedulers.fromExecutorService(
      Executors.newFixedThreadPool(SERVICE_THREAD_POOL_SIZE,
          newNamedThreadFactory(SERVICE_THREAD_PREFIX))
  );

  public static final Scheduler TEST_SCHEDULER = Schedulers.fromExecutorService(
      Executors.newFixedThreadPool(TEST_THREAD_POOL_SIZE,
          newNamedThreadFactory(TEST_THREAD_PREFIX))
  );

  private static ThreadFactory newNamedThreadFactory(final String prefix) {
    return new ThreadFactory() {
      private final ThreadFactory defaultFactory = Executors.defaultThreadFactory();
      private final AtomicInteger count = new AtomicInteger(0);

      @Override
      public Thread newThread(Runnable r) {
        Thread thread = defaultFactory.newThread(r);
        thread.setName(prefix + "-" + count.incrementAndGet());
        return thread;
      }
    };
  }

}
