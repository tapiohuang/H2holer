package org.hycode.h2holer.client;

import org.hycode.h2holer.client.handlers.ClientHandler;
import org.hycode.h2holer.client.servers.ClientServer;
import org.hycode.h2holer.client.servers.IntraServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;


public class H2holerClientApplication {
    private static Logger log = LoggerFactory.getLogger(ClientHandler.class);

    public static void main(String[] args) {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        log.info("Client PID:{}", pid);

        ClientServer clientServer = ClientServer.get();
        clientServer.init();

        IntraServer intraServer = IntraServer.get();
        intraServer.init();

    }
}
