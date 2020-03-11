package org.hycode.h2holer.server.services.servers;

import org.hycode.h2holer.server.services.contexts.ClientContext;

import java.util.HashMap;

public class ClientServer extends AbstractServer {
    private static final HashMap<Integer, ClientContext> portAndClientContextMap = new HashMap<>();
    private static ClientServer clientServer;

    private ClientServer() {
    }

    public static ClientServer get() {
        if (clientServer == null) {
            clientServer = new ClientServer();
        }
        return clientServer;
    }


}
