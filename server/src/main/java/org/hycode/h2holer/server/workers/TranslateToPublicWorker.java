package org.hycode.h2holer.server.workers;

import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.workers.BaseWorker;
import org.hycode.h2holer.server.services.publicly.PublicContext;

import java.io.*;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TranslateToPublicWorker extends BaseWorker {
    private final HashMap<String, PublicContext> publicContextMap;
    private final ConcurrentLinkedQueue<H2holerMessage> messageQueue;
    private boolean process;


    public TranslateToPublicWorker() {
        publicContextMap = new HashMap<>();
        messageQueue = new ConcurrentLinkedQueue<>();
    }

    public int getSize() {
        return messageQueue.size();
    }

    @Override
    public void main() {
        if (messageQueue.isEmpty()) {
            this.pause();
            process = false;
        } else {
            process = true;
            H2holerMessage h2holerMessage = messageQueue.poll();
            PublicContext publicContext = publicContextMap.get(h2holerMessage.getSn());
            if (publicContext != null) {
                //System.out.println(new String(h2holerMessage.getData()));
                publicContext.send(h2holerMessage);
            }
        }
    }

    public void addMessage(H2holerMessage h2holerMessage) {
        messageQueue.offer(h2holerMessage);
        this.start();
    }


    public void registerPublicContext(PublicContext publicContext) {
        publicContextMap.put(publicContext.getSn(), publicContext);
    }

    public void unRegisterPublicContext(PublicContext publicContext) {
        publicContextMap.remove(publicContext.getSn());
    }

    public boolean isProcess() {
        return process;
    }
}
