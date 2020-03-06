package org.hycode.h2holer.server.services.managers;

import io.netty.channel.Channel;
import org.hycode.h2holer.server.services.contexts.ClientContext;
import org.hycode.h2holer.server.services.workers.ClientHandleWorker;
import org.hycode.h2holer.server.services.workers.WorkerGroup;

import java.util.HashMap;

public class ClientManager {
    private static ClientManager clientManager;
    private final HashMap<String, ClientContext> clientIdMap;

    private ClientManager() {
        clientIdMap = new HashMap<>();
    }

    public static ClientManager get() {
        if (clientManager == null) {
            clientManager = new ClientManager();
        }
        return clientManager;
    }

    public ClientContext applyClientContext(Channel channel) {
        ClientContext clientContext = new ClientContext();
        clientContext.setChannel(channel);
        ClientHandleWorker clientHandleWorker = WorkerGroup.get().getClientHandleWorker();
        clientContext.setClientHandleWorker(clientHandleWorker);
        return clientContext;
    }

    public void destroyClientContext(ClientContext clientContext) {
        clientIdMap.remove(clientContext.getClientId());
        clientContext.getPublicContexts().forEach(v -> {
            PublicManager.get().destroyPublicContext(v);
        });
        clientContext.close();
    }

    public ClientContext searchClientContext(String clientId) {
        return clientIdMap.get(clientId);
    }

    public void registerClientContext(ClientContext clientContext) {
        clientIdMap.put(clientContext.getClientId(), clientContext);
    }

}
