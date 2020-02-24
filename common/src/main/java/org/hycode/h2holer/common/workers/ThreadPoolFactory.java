package org.hycode.h2holer.common.workers;

import com.sun.istack.internal.NotNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author HuangXiaojie
 */
public class ThreadPoolFactory implements ThreadFactory {
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public ThreadPoolFactory(String name) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = name + "-";
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread t;
        t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        return t;
    }
}
