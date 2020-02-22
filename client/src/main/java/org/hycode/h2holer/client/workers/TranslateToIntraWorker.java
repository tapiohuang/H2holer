package org.hycode.h2holer.client.workers;


import org.hycode.h2holer.client.IntraContext;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.workers.BaseWorker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TranslateToIntraWorker extends BaseWorker {
    private final ConcurrentLinkedQueue<H2holerMessage> messageQueue;
    private final LinkedList<H2holerMessage> messageWaitList;
    private final HashMap<String, IntraContext> intraContextMap;

    public TranslateToIntraWorker() {
        messageQueue = new ConcurrentLinkedQueue<>();
        intraContextMap = new HashMap<>();
        messageWaitList = new LinkedList<>();
    }

    @Override
    public void main() {
        while (true) {
            if (messageQueue.isEmpty()) {
                this.pause();
                return;
            } else {
                H2holerMessage h2holerMessage = messageQueue.poll();
                IntraContext intraContext = intraContextMap.get(h2holerMessage.getSn());
                boolean status = false;
                if (intraContext != null) {
                    status = intraContext.isOk();
                }
                if (status) {
                    intraContext.send(h2holerMessage);
                } else {
                    messageWaitList.addLast(h2holerMessage);
                }
            }
        }
    }

    public void registerIntraContext(IntraContext intraContext) {
        intraContextMap.put(intraContext.getSn(), intraContext);
        messageQueue.addAll(messageWaitList);
        messageWaitList.clear();
        this.start();
    }

    public void addMessage(H2holerMessage h2holerMessage) {
        messageQueue.add(h2holerMessage);
        this.start();
    }

    public void unRegisterIntraContext(String sn) {
        intraContextMap.remove(sn);
    }
}
