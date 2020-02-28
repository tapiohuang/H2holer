package org.hycode.h2holer.client.servers;

import org.hycode.h2holer.client.contexts.ClientContext;
import org.hycode.h2holer.client.inits.ClientInitializer;
import org.hycode.h2holer.client.linsteners.ClientAuthListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientServer extends AbstractServer {
    private static final Logger log = LoggerFactory.getLogger(ClientServer.class);
    public static ClientServer clientServer;

    private ClientServer() {
    }

    public static ClientServer get() {
        if (clientServer == null) {
            clientServer = new ClientServer();
        }
        return clientServer;
    }


    @Override
    public void init() {
        try {
            bootstrap.handler(new ClientInitializer());
            bootstrap.connect("127.0.0.1", 10000).addListener(new ClientAuthListener()).sync();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
