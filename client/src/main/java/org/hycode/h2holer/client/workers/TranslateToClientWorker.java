package org.hycode.h2holer.client.workers;

import org.hycode.h2holer.client.ClientContext;
import org.hycode.h2holer.client.headlers.IntraHandler;
import org.hycode.h2holer.client.managers.ClientService;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.workers.BaseWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TranslateToClientWorker extends BaseWorker {
    private final ConcurrentLinkedQueue<H2holerMessage> messageQueue;
    private final ClientContext clientContext;
    private static Logger log = LoggerFactory.getLogger(TranslateToClientWorker.class);

    public TranslateToClientWorker() {
        messageQueue = new ConcurrentLinkedQueue<>();
        this.clientContext = ClientService.getClientContext();
    }

    @Override
    public void main() {
        while (true) {
            if (messageQueue.isEmpty()) {
                this.pause();
                return;
            } else {
                H2holerMessage h2holerMessage = messageQueue.poll();
                //System.out.println(new String(h2holerMessage.getData()));
                clientContext.send(h2holerMessage);
            }
        }
    }

    public void addMessage(H2holerMessage h2holerMessage) {
        messageQueue.offer(h2holerMessage);
        this.start();
    }
}
