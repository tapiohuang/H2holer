package org.hycode.h2holer.client.headlers;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import org.hycode.h2holer.common.modles.H2holerMessage;

public class ClientHandler extends SimpleChannelInboundHandler<H2holerMessage> {
    protected void channelRead0(ChannelHandlerContext ctx, H2holerMessage msg) throws Exception {
        System.out.println(msg);
    }
}
