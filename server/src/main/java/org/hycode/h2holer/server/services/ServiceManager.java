package org.hycode.h2holer.server.services;


import org.hycode.h2holer.common.modles.H2holerResult;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.hycode.h2holer.server.services.client.ClientContext;

import java.util.HashMap;

public class ServiceManager {
    private static final ServiceManager serviceManager = new ServiceManager();
    private final HashMap<Integer, ClientContext> portAndClientContextMap;

    public ServiceManager() {
        portAndClientContextMap = new HashMap<>();
    }

    public static H2holerResult<ClientContext> getClientContextByPort(int port) {
        ClientContext clientContext = serviceManager.portAndClientContextMap.get(port);
        if (clientContext == null) {
            return CommonUtil.fail("Client No Connect");
        } else {
            return CommonUtil.success(clientContext);
        }
    }


    public static void registerClientContext(ClientContext clientContext) {
        clientContext.getPortMapperMap().forEach((port, portMapper) -> {
            serviceManager.portAndClientContextMap.put(port, clientContext);
        });
    }
}
