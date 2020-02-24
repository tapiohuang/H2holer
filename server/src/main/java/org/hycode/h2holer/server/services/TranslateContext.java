package org.hycode.h2holer.server.services;

import org.hycode.h2holer.common.modles.H2holerMessage;

public class TranslateContext {
    private final H2holerMessage h2holerMessage;
    private final ServiceContext serviceContext;

    public TranslateContext(H2holerMessage h2holerMessage, ServiceContext serviceContext) {
        this.h2holerMessage = h2holerMessage;
        this.serviceContext = serviceContext;
    }

    public H2holerMessage getH2holerMessage() {
        return h2holerMessage;
    }

    public ServiceContext getServiceContext() {
        return serviceContext;
    }
}
