package org.hycode.h2holer.server.services.publicly;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpServiceHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static final Logger log = LoggerFactory.getLogger(HttpServiceHandler.class);
    private final PublicContext publicContext;
    private String data = "";

    public HttpServiceHandler(PublicContext publicContext) {
        this.publicContext = publicContext;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        int len = msg.readableBytes();
        byte[] bytes = new byte[len];
        msg.readBytes(bytes);
        String s = new String(bytes);
        data += s;
    }

    @Override
    public synchronized void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("HTTP收到数据:{}", data);
        H2holerMessage h2holerMessage = CommonUtil.message(H2holerMessage.DATA, data);
        data = "";
        publicContext.sendClient(h2holerMessage);
        super.channelReadComplete(ctx);
    }
}
