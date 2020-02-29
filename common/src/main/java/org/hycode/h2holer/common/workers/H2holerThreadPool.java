package org.hycode.h2holer.common.workers;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class H2holerThreadPool {
    private static final H2holerThreadPool h2holerThreadPool = new H2holerThreadPool();
    private ThreadPoolExecutor pool;

    public H2holerThreadPool() {
        pool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadPoolFactory("工作线程")
        );
    }

    public static void addWorker(BaseWorker baseWorker) {
        h2holerThreadPool.pool.execute(baseWorker);
    }

}
