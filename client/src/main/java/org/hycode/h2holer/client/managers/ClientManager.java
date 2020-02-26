package org.hycode.h2holer.client.managers;

import io.netty.channel.Channel;
import org.hycode.h2holer.client.contexts.ClientContext;
import org.hycode.h2holer.client.workers.ClientHandleWorker;
import org.hycode.h2holer.client.workers.WorkerGroup;

public class ClientManager {
    private static ClientManager clientManager;
    private ClientContext clientContext;

    public static ClientManager get() {
        if (clientManager == null) {
            clientManager = new ClientManager();
        }
        return clientManager;
    }

    public ClientContext applyClientContext(Channel channel) {
        clientContext = new ClientContext();
        clientContext.setChannel(channel);
        ClientHandleWorker clientHandleWorker = WorkerGroup.get().getClientHandleWorker();
        clientContext.setClientHandleWorker(clientHandleWorker);
        return clientContext;
    }

    public ClientContext getClientContext() {
        return clientContext;
    }
}
