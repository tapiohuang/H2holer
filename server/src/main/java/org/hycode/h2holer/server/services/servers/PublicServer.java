package org.hycode.h2holer.server.services.servers;

public class PublicServer extends AbstractServer {
    private static PublicServer publicServer;

    private PublicServer() {
    }

    public static PublicServer get() {
        if (publicServer == null) {
            publicServer = new PublicServer();
        }
        return publicServer;
    }


}

