package org.hycode.h2holer.server.services.managers;


import org.hycode.h2holer.server.models.H2holerPortMapper;
import org.hycode.h2holer.server.services.contexts.ClientContext;

import java.util.HashMap;

public class ServerManager {
    private static ServerManager serverManager;
    private final HashMap<Integer, String> portClientMap;
    private final HashMap<Integer, H2holerPortMapper> portMapperMap;

    public ServerManager() {
        portClientMap = new HashMap<>();
        portMapperMap = new HashMap<>();
    }

    public static ServerManager get() {
        if (serverManager == null) {
            serverManager = new ServerManager();
        }
        return serverManager;
    }

    public void registerPort(int publicPort, H2holerPortMapper h2holerPortMapper) {
        portMapperMap.put(publicPort, h2holerPortMapper);

    }

    public String searchClientId(int port) {
        return portClientMap.get(port);
    }

    public void registerClient(int publicPort, ClientContext clientContext) {
        portClientMap.put(publicPort, clientContext.getClientId());
    }

    public H2holerPortMapper searchPortMapper(int port) {
        return portMapperMap.get(port);
    }
}
