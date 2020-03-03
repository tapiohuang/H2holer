package org.hycode.h2holer.client.workers;

import org.hycode.h2holer.client.contexts.ClientContext;
import org.hycode.h2holer.common.handlers.MessageHandler;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.modles.MsgEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public class ClientHandleWorker implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandleWorker.class);
    private final LinkedList<Entry> waitMessageList;
    private final LinkedList<MessageHandler<Entry>> messageHandlers;
    private final Object waitLock = new Object();

    public ClientHandleWorker() {
        waitMessageList = new LinkedList<>();
        messageHandlers = new LinkedList<>();
    }


    public void handleMessage(ClientContext clientContext, H2holerMessage msg) {
        waitMessageList.add(new Entry(clientContext, msg));
        synchronized (waitLock) {
            waitLock.notifyAll();
        }
    }

    public void addHandler(MessageHandler<Entry> messageHandler) {
        messageHandlers.add(messageHandler);
    }

    public void pause() {
        try {
            synchronized (waitLock) {
                waitLock.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                main();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private void main() {
        if (waitMessageList.isEmpty()) {
            this.pause();
        }
        Entry msgEntry;
        synchronized (waitMessageList) {
            msgEntry = waitMessageList.poll();

            if (msgEntry == null) {
                waitMessageList.clear();
                return;
            }
        }
        for (MessageHandler<Entry> messageHandler : messageHandlers
        ) {
            if (messageHandler.onMessage(msgEntry)) {
                messageHandler.handleMessage(msgEntry);
                return;
            }
        }
        logger.error("无法处理信息:{}", msgEntry.getH2holerMessage());
    }


    public static class Entry implements MsgEntry {
        private final ClientContext clientContext;
        private final H2holerMessage h2holerMessage;

        public Entry(ClientContext clientContext, H2holerMessage h2holerMessage) {
            this.clientContext = clientContext;
            this.h2holerMessage = h2holerMessage;
        }


        public H2holerMessage getH2holerMessage() {
            return h2holerMessage;
        }

        public ClientContext getClientContext() {
            return clientContext;
        }
    }

}
