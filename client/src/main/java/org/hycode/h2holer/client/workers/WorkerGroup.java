package org.hycode.h2holer.client.workers;

import org.hycode.h2holer.client.handlers.ClientMessageHandler;
import org.hycode.h2holer.client.handlers.IntraMessageHandler;
import org.hycode.h2holer.common.workers.ThreadPoolFactory;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WorkerGroup {
    private static WorkerGroup workerGroup;
    private final ThreadPoolExecutor pool;
    private final LinkedList<ClientHandleWorker> clientHandleWorkers;
    private final int MAX_WORKER = 1;
    private int i = 0;

    public WorkerGroup() {
        clientHandleWorkers = new LinkedList<>();
        pool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadPoolFactory("ClientHandleWorker")
        );
    }

    public static WorkerGroup get() {
        if (workerGroup == null) {
            workerGroup = new WorkerGroup();
            workerGroup.initializer();
        }
        return workerGroup;
    }

    private void initializer() {
        for (int i = 0; i < MAX_WORKER; i++) {
            ClientHandleWorker clientHandleWorker = new ClientHandleWorker();
            clientHandleWorkers.add(clientHandleWorker);
            /**
             * 临时
             */
            clientHandleWorker.addHandler(new ClientMessageHandler());
            clientHandleWorker.addHandler(new IntraMessageHandler());
            //clientHandleWorker.addHandler(new ClientIntraHandler());

            pool.execute(clientHandleWorker);
        }
    }

    public ClientHandleWorker getClientHandleWorker() {
        if (i >= MAX_WORKER) {
            i = 0;
        }
        return clientHandleWorkers.get(i);
    }

}
