package org.hycode.h2holer.client.servers;

import org.hycode.h2holer.client.inits.IntraInitializer;
import org.hycode.h2holer.client.linsteners.IntraConnectListener;
import org.hycode.h2holer.common.modles.H2holerPublicConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntraServer extends AbstractServer {
    private static final Logger log = LoggerFactory.getLogger(IntraServer.class);
    public static IntraServer intraServer;

    private IntraServer() {
        super();
    }

    public static IntraServer get() {
        if (intraServer == null) {
            intraServer = new IntraServer();
        }
        return intraServer;
    }


    @Override
    public void init() {
        bootstrap.handler(new IntraInitializer());
    }


    public void connectIntra(H2holerPublicConfig h2holerPublicConfig) {
        bootstrap.connect(h2holerPublicConfig.getHost(), h2holerPublicConfig.getPort()).addListener(new IntraConnectListener(h2holerPublicConfig));
    }
}
