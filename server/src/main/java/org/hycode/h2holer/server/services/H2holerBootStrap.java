package org.hycode.h2holer.server.services;

import org.hycode.h2holer.server.services.client.ClientInitializer;
import org.hycode.h2holer.server.services.publicly.PublicInitializer;
import org.hycode.h2holer.server.services.servers.ClientServer;
import org.hycode.h2holer.server.services.servers.PublicServer;
import org.hycode.h2holer.server.services.workers.WorkerGroup;

public class H2holerBootStrap {


    public void initializer() {
        PublicServer publicServer = PublicServer.get();
        publicServer.start(new PublicInitializer());
        publicServer.listenPort(65);


        ClientServer clientServer = ClientServer.get();
        clientServer.start(ClientInitializer.getClientInitializer());
        clientServer.listenPort(10000);


        WorkerGroup workerGroup = WorkerGroup.get();
    }
}
