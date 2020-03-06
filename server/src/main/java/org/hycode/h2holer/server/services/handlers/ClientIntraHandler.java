package org.hycode.h2holer.server.services.handlers;

import io.netty.channel.Channel;
import org.hycode.h2holer.common.handlers.MessageHandler;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.hycode.h2holer.server.services.contexts.ClientContext;
import org.hycode.h2holer.server.services.contexts.PublicContext;
import org.hycode.h2holer.server.services.managers.PublicManager;
import org.hycode.h2holer.server.services.workers.ClientHandleWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientIntraHandler implements MessageHandler<ClientHandleWorker.Entry> {
    private static final Logger log = LoggerFactory.getLogger(ClientIntraHandler.class);

    @Override
    public boolean onMessage(ClientHandleWorker.Entry msgEntry) {
        return msgEntry.getH2holerMessage().getType() >= 100 && msgEntry.getH2holerMessage().getType() < 200;
    }

    @Override
    public void handleMessage(ClientHandleWorker.Entry msgEntry) {
        int type = msgEntry.getH2holerMessage().getType();
        H2holerMessage h2holerMessage = msgEntry.getH2holerMessage();
        ClientContext clientContext = msgEntry.getClientContext();
        Channel channel = clientContext.getChannel();

        switch (type) {
            case H2holerMessage.INTRA_INIT:
            case H2holerMessage.INTRA_DISCONNECT:
            case H2holerMessage.INTRA_DATA:
                channel.writeAndFlush(h2holerMessage);
                break;
            case H2holerMessage.INTRA_CLOSE:
                PublicManager.get().destroyPublicContext(h2holerMessage.getSn());
                break;
            case H2holerMessage.INTRA_RETURN_DATA:
                PublicContext publicContext = PublicManager.get().getPublicContext(h2holerMessage.getSn());
                if (publicContext == null) {
                    log.info("publicContext is null");
                    return;
                }
                publicContext.getChannel().writeAndFlush(CommonUtil.toByteBuf(h2holerMessage.getData()));
                break;
        }
    }


}
