package org.hycode.h2holer.client.contexts;

import io.netty.channel.Channel;
import org.hycode.h2holer.client.utils.ClientUtil;
import org.hycode.h2holer.client.workers.ClientHandleWorker;
import org.hycode.h2holer.common.modles.H2holerMessage;

public class ClientContext {
    private Channel channel;
    private String clientId;
    private ClientHandleWorker clientHandleWorker;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void close() {
        this.channel.close();
        ClientUtil.exit();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientHandleWorker(ClientHandleWorker clientHandleWorker) {
        this.clientHandleWorker = clientHandleWorker;
    }

    public void handleMessage(H2holerMessage msg) {
        this.clientHandleWorker.handleMessage(this, msg);
    }
}
