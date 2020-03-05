package org.hycode.h2holer.server.services.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.server.services.contexts.ClientContext;
import org.hycode.h2holer.server.services.managers.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler extends SimpleChannelInboundHandler<H2holerMessage> {
    private static final Logger log = LoggerFactory.getLogger(ClientHandler.class);
    private ClientContext clientContext;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, H2holerMessage msg) throws Exception {
        clientContext.handleMessage(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("通道激活");
        clientContext = ClientManager.get().applyClientContext(ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("通道关闭");
        ClientManager.get().destroyClientContext(clientContext);
        super.channelInactive(ctx);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info(cause.getMessage());
    }


}
