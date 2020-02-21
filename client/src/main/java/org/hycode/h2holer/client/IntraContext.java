package org.hycode.h2holer.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IntraContext {
    private static Logger logger = LoggerFactory.getLogger(IntraContext.class);
    private Channel channel;
    private ClientContext clientContext;
    private String sn;

    public void registerChannel(Channel channel) {
        this.channel = channel;
    }

    public void send(H2holerMessage h2holerMessage) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(h2holerMessage.getData());
        channel.writeAndFlush(byteBuf);
    }

    public void sendClient(H2holerMessage h2holerMessage) {
        h2holerMessage.setSn(this.sn);
        h2holerMessage.setNo(0);
        clientContext.send(h2holerMessage);
    }

    public void registerClientContext(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void close() {
        logger.info("{}关闭", sn);
        this.channel.close();
    }
}
