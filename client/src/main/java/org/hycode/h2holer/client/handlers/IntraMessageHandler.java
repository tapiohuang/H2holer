package org.hycode.h2holer.client.handlers;

import io.netty.channel.Channel;
import org.hycode.h2holer.client.contexts.ClientContext;
import org.hycode.h2holer.client.contexts.IntraContext;
import org.hycode.h2holer.client.managers.ClientManager;
import org.hycode.h2holer.client.managers.IntraManager;
import org.hycode.h2holer.client.servers.IntraServer;
import org.hycode.h2holer.client.workers.ClientHandleWorker;
import org.hycode.h2holer.common.handlers.MessageHandler;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.modles.H2holerPublicConfig;
import org.hycode.h2holer.common.utils.CommonUtil;

public class IntraMessageHandler implements MessageHandler<ClientHandleWorker.Entry> {
    @Override
    public boolean onMessage(ClientHandleWorker.Entry msgEntry) {
        return msgEntry.getH2holerMessage().getType() >= 100 && msgEntry.getH2holerMessage().getType() < 200;
    }

    @Override
    public void handleMessage(ClientHandleWorker.Entry msgEntry) {
        int type = msgEntry.getH2holerMessage().getType();
        H2holerMessage h2holerMessage = msgEntry.getH2holerMessage();
        ClientContext clientContext = ClientManager.get().getClientContext();
        Channel channel = clientContext.getChannel();

        switch (type) {
            case H2holerMessage.INTRA_INIT:
                this.handleInitIntraContext(h2holerMessage);
                break;
            case H2holerMessage.INTRA_DISCONNECT:
                this.handleIntraDisConnect(h2holerMessage);
                break;
            case H2holerMessage.INTRA_DATA:
                System.out.println(new String(h2holerMessage.getData()));
                this.handleIntraData(h2holerMessage);
                break;
            case H2holerMessage.INTRA_CLOSE:
            case H2holerMessage.INTRA_RETURN_DATA:
                channel.writeAndFlush(h2holerMessage);
                break;
        }
    }

    private void handleInitIntraContext(H2holerMessage h2holerMessage) {
        String publicId = h2holerMessage.getSn();
        IntraManager.get().applyIntraContext(publicId);
        H2holerPublicConfig h2holerPublicConfig = CommonUtil.fromJson(new String(h2holerMessage.getData()), H2holerPublicConfig.class);
        IntraServer.get().connectIntra(h2holerPublicConfig);
    }

    private void handleIntraData(H2holerMessage h2holerMessage) {
        String publicId = h2holerMessage.getSn();
        IntraContext intraContext = IntraManager.get().getIntraContext(publicId);
        intraContext.addMessageCache(h2holerMessage);
        //System.out.println(new String(h2holerMessage.getData()));
        //intraContext.getChannel().writeAndFlush(CommonUtil.toByteBuf(h2holerMessage.getData()));
    }

    private void handleIntraDisConnect(H2holerMessage h2holerMessage) {
        IntraContext intraContext = IntraManager.get().getIntraContext(h2holerMessage.getSn());
        if (intraContext == null) {
            return;
        }
        IntraManager.get().closeIntraContext(intraContext);
    }
}
