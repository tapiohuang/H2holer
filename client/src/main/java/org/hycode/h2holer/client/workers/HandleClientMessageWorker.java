package org.hycode.h2holer.client.workers;

import org.hycode.h2holer.client.headlers.ClientMessageHandler;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.workers.BaseWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

public class HandleClientMessageWorker extends BaseWorker {
    private static Logger log = LoggerFactory.getLogger(HandleClientMessageWorker.class);
    private final ConcurrentLinkedQueue<H2holerMessage> messageQueue;
    private final ClientMessageHandler clientMessageHandler;

    public HandleClientMessageWorker() {
        messageQueue = new ConcurrentLinkedQueue<>();
        clientMessageHandler = new ClientMessageHandler();
    }

    public void addMessage(H2holerMessage h2holerMessage) {
        messageQueue.offer(h2holerMessage);
        this.start();
    }

    @Override
    public void main() {
        while (true) {
            if (messageQueue.isEmpty()) {
                this.pause();
                return;
            } else {
                H2holerMessage h2holerMessage = messageQueue.poll();
                clientMessageHandler.handle(h2holerMessage);
            }
        }
    }
}
