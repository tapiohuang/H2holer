package org.hycode.h2holer.client.managers;

import org.hycode.h2holer.client.inits.IntraInitializer;
import org.hycode.h2holer.client.linsteners.IntraConnectListener;
import org.hycode.h2holer.common.modles.H2holerPublicConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntraManager extends AbstractManager {
    private final static IntraManager intraManager = new IntraManager();

    private static final Logger log = LoggerFactory.getLogger(IntraManager.class);

    private IntraManager() {
        super();
    }

    public static IntraManager get() {
        return intraManager;
    }


    public static void connectIntra(H2holerPublicConfig h2holerPublicConfig) {
        intraManager.bootstrap.connect(h2holerPublicConfig.getHost(), h2holerPublicConfig.getPort()).addListener(new IntraConnectListener(h2holerPublicConfig));
    }

    @Override
    public void init() {
        bootstrap.handler(new IntraInitializer());
    }
}
