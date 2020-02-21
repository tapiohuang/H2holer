package org.hycode.h2holer.client;

import io.netty.channel.Channel;
import org.hycode.h2holer.client.utils.ClientUtil;
import org.hycode.h2holer.common.modles.H2holerMessage;

import java.util.HashMap;

public class ClientContext {
    private final HashMap<String, IntraContext> intraContextMap;
    private Channel channel;

    public ClientContext() {
        intraContextMap = new HashMap<>();
    }


    public void send(H2holerMessage h2holerMessage) {
        channel.writeAndFlush(h2holerMessage);
    }

    public void registerChannel(Channel channel) {
        this.channel = channel;
    }

    public void sendIntra(String sn, H2holerMessage h2holerMessage) {
        intraContextMap.get(sn).send(h2holerMessage);
    }

    public void close() {
        intraContextMap.forEach((sn, intraContext) -> {
            intraContext.close();
        });
        this.channel.close();
        ClientUtil.exit();
    }

    public void registerIntraContext(IntraContext intraContext) {
        intraContextMap.put(intraContext.getSn(), intraContext);
        intraContext.registerClientContext(this);
    }

    public void sendIntra(H2holerMessage h2holerMessage) {
        this.sendIntra(h2holerMessage.getSn(), h2holerMessage);
    }

    public void closeIntra(H2holerMessage h2holerMessage) {
        intraContextMap.get(h2holerMessage.getSn()).close();
    }
}
