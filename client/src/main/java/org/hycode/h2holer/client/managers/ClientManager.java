package org.hycode.h2holer.client.managers;

import org.hycode.h2holer.client.ClientContext;
import org.hycode.h2holer.client.inits.ClientInitializer;
import org.hycode.h2holer.client.linsteners.ClientAuthListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientManager extends AbstractManager {
    public static final ClientManager clientManager = new ClientManager();

    private static final Logger log = LoggerFactory.getLogger(ClientManager.class);

    private ClientContext clientContext;

    private ClientManager() {
    }

    public static ClientContext getClientContext() {
        return clientManager.clientContext;
    }

    public static ClientManager get() {
        return clientManager;
    }

    public void setOk() {

    }

    @Override
    public void init() {
        try {
            clientContext = new ClientContext();
            bootstrap.handler(new ClientInitializer());
            bootstrap.connect("127.0.0.1", 10000).addListener(new ClientAuthListener(clientContext)).sync();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
