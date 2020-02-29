package org.hycode.h2holer.common.workers;

public abstract class BaseWorker implements Runnable {
    private final Object waitLock = new Object();
    private boolean run = false;
    private boolean stop = false;


    public void start() {
        synchronized (this) {
            if (!this.run) {
                synchronized (waitLock) {
                    waitLock.notify();
                }
                this.run = true;
            }
        }
    }

    public void pause() {
        synchronized (this) {
            run = false;
        }
    }

    public void stop() {
        this.stop = true;
    }


    @Override
    public void run() {
        try {
            while (!stop) {
                synchronized (waitLock) {
                    if (run) {
                        main();
                    } else {
                        waitLock.wait();
                    }
                }
            }
            stop = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void main();
}
