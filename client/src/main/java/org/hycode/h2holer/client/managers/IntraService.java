package org.hycode.h2holer.client.managers;

import org.hycode.h2holer.client.IntraContext;
import org.hycode.h2holer.client.inits.IntraInitializer;
import org.hycode.h2holer.client.linsteners.IntraConnectListener;
import org.hycode.h2holer.client.workers.TranslateToIntraWorker;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.modles.H2holerPublicConfig;
import org.hycode.h2holer.common.workers.H2holerThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class IntraService extends AbstractService {
    public final static IntraService intraService = new IntraService();
    private static final Logger log = LoggerFactory.getLogger(IntraService.class);
    private static final HashMap<String, IntraContext> intraContextMap = new HashMap<>();
    private static TranslateToIntraWorker translateToIntraWorker;

    private IntraService() {
        super();
    }


    public static void addMessage(H2holerMessage h2holerMessage) {
        translateToIntraWorker.addMessage(h2holerMessage);
    }

    public static void connectIntra(H2holerPublicConfig h2holerPublicConfig) {
        intraService.bootstrap.connect(h2holerPublicConfig.getHost(), h2holerPublicConfig.getPort()).addListener(new IntraConnectListener(h2holerPublicConfig));
    }

    public static void registerIntraContext(IntraContext intraContext) {
        intraContextMap.put(intraContext.getSn(), intraContext);
        translateToIntraWorker.registerIntraContext(intraContext);
    }

    public static void closeIntra(H2holerMessage h2holerMessage) {
        translateToIntraWorker.unRegisterIntraContext(h2holerMessage.getSn());
        intraContextMap.get(h2holerMessage.getSn()).close();
    }

    @Override
    public void init() {
        bootstrap.handler(new IntraInitializer());
        translateToIntraWorker = new TranslateToIntraWorker();

        H2holerThreadPool.addWorker(translateToIntraWorker);

        translateToIntraWorker.start();
    }


}
