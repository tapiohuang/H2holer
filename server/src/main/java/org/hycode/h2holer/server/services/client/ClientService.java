package org.hycode.h2holer.server.services.client;

import org.hycode.h2holer.server.services.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientService extends AbstractService {


    private ClientService() {
    }

    private static final ClientService clientService = new ClientService();

    public static ClientService get() {
        return clientService;
    }


}
