package org.hycode.h2holer.client.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.hycode.h2holer.client.contexts.ClientContext;
import org.hycode.h2holer.client.managers.ClientManager;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler extends SimpleChannelInboundHandler<H2holerMessage> {
    private static Logger log = LoggerFactory.getLogger(ClientHandler.class);

    private ClientContext clientContext;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        clientContext.close();
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("错误:{}", cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }

    protected void channelRead0(ChannelHandlerContext ctx, H2holerMessage msg) throws Exception {
        clientContext = ClientManager.get().getClientContext();
        clientContext.handleMessage(msg);

    }


}
