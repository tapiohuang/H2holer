package org.hycode.h2holer.server.workers;

import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.workers.BaseWorker;
import org.hycode.h2holer.server.services.client.ClientContext;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 将队列中的信息发送到Client
 */
public class TranslateToClientWorker extends BaseWorker {
    private final ConcurrentLinkedQueue<H2holerMessage> messageQueue;
    private final LinkedList<H2holerMessage> messageWaitList;
    private final HashMap<String, ClientContext> clientContextMap;
    private boolean process;

    public TranslateToClientWorker() {
        messageQueue = new ConcurrentLinkedQueue<>();
        clientContextMap = new HashMap<>();
        messageWaitList = new LinkedList<>();
    }

    public void addMessage(H2holerMessage h2holerMessage) {
        messageQueue.offer(h2holerMessage);
        this.start();
    }

    public void registerClientContext(ClientContext clientContext) {
        clientContextMap.put(clientContext.getClientId(), clientContext);
    }

    public void unRegisterClientContext(ClientContext clientContext) {
        clientContextMap.remove(clientContext.getClientId());
    }

    public int getSize() {
        return messageQueue.size();
    }

    @Override
    public void main() {
        if (messageQueue.isEmpty()) {
            this.pause();
            process = false;
            return;
        }
        process = true;
        H2holerMessage h2holerMessage = messageQueue.poll();
        if (h2holerMessage != null) {
            String clientId = h2holerMessage.getClientId();
            ClientContext clientContext = clientContextMap.get(clientId);
            if (clientContext != null) {
                clientContext.send(h2holerMessage);
            }
        }
    }


    public boolean isProcess() {
        return process;
    }
}
