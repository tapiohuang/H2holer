package org.hycode.h2holer.client;

import io.netty.channel.Channel;
import org.hycode.h2holer.client.utils.ClientUtil;
import org.hycode.h2holer.common.modles.H2holerMessage;

public class ClientContext {
    private Channel channel;
    private String clientId;

    public void registerChannel(Channel channel) {
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

    public void send(H2holerMessage h2holerMessage) {
        channel.writeAndFlush(h2holerMessage);
    }
}
