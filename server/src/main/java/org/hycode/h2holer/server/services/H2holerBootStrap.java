package org.hycode.h2holer.server.services;

import org.hycode.h2holer.server.services.client.ClientService;
import org.hycode.h2holer.server.services.client.ClientServiceInitializer;
import org.hycode.h2holer.server.services.publicly.PublicService;
import org.hycode.h2holer.server.services.publicly.PublicServiceInitializer;

public class H2holerBootStrap {

    public void initializer() {
        PublicService publicService = PublicService.get();
        publicService.start(new PublicServiceInitializer());
        publicService.listenPort(65);


        ClientService clientService = ClientService.get();
        clientService.start(ClientServiceInitializer.getClientServiceInitializer());
        clientService.listenPort(10000);
    }
}
