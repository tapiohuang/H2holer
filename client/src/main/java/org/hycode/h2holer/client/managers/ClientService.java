package org.hycode.h2holer.client.managers;

import org.hycode.h2holer.client.ClientContext;
import org.hycode.h2holer.client.inits.ClientInitializer;
import org.hycode.h2holer.client.linsteners.ClientAuthListener;
import org.hycode.h2holer.client.workers.HandleClientMessageWorker;
import org.hycode.h2holer.client.workers.TranslateToClientWorker;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.workers.H2holerThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientService extends AbstractService {
    public static final ClientService clientService = new ClientService();
    private static final Logger log = LoggerFactory.getLogger(ClientService.class);
    private static HandleClientMessageWorker handleClientMessageWorker;
    private static TranslateToClientWorker translateToClientWorker;
    private ClientContext clientContext;

    private ClientService() {
    }

    public static ClientContext getClientContext() {
        return clientService.clientContext;
    }

    
    public static void addClientMessage(H2holerMessage h2holerMessage) {
        translateToClientWorker.addMessage(h2holerMessage);
    }

    public static void addHandlerMessage(H2holerMessage h2holerMessage) {
        handleClientMessageWorker.addMessage(h2holerMessage);
    }

    @Override
    public void init() {
        try {
            clientContext = new ClientContext();
            bootstrap.handler(new ClientInitializer());
            bootstrap.connect("127.0.0.1", 10000).addListener(new ClientAuthListener()).sync();

            translateToClientWorker = new TranslateToClientWorker();
            handleClientMessageWorker = new HandleClientMessageWorker();

            H2holerThreadPool.addWorker(handleClientMessageWorker);
            H2holerThreadPool.addWorker(translateToClientWorker);

            handleClientMessageWorker.start();
            translateToClientWorker.start();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
