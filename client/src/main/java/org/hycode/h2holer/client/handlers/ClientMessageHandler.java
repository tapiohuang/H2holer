package org.hycode.h2holer.client.handlers;


import io.netty.channel.Channel;
import org.hycode.h2holer.client.contexts.ClientContext;
import org.hycode.h2holer.client.utils.ClientUtil;
import org.hycode.h2holer.client.workers.ClientHandleWorker;
import org.hycode.h2holer.common.handlers.MessageHandler;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientMessageHandler implements MessageHandler<ClientHandleWorker.Entry> {
    private static Logger log = LoggerFactory.getLogger(ClientMessageHandler.class);

    @Override
    public boolean onMessage(ClientHandleWorker.Entry msgEntry) {
        return msgEntry.getH2holerMessage().getType() < 100;
    }

    @Override
    public void handleMessage(ClientHandleWorker.Entry msgEntry) {
        H2holerMessage h2holerMessage = msgEntry.getH2holerMessage();
        ClientContext clientContext = msgEntry.getClientContext();
        Channel channel = clientContext.getChannel();
        int type = h2holerMessage.getType();
        switch (type) {
            case H2holerMessage.AUTH:
                channel.writeAndFlush(h2holerMessage);
                break;
            case H2holerMessage.AUTH_SUCCESS:
                log.info("鉴权成功");
                clientContext.setClientId(h2holerMessage.getClientId());
                break;
            case H2holerMessage.AUTH_FAIL:
                ClientUtil.exit();
                break;
        }
    }
}
