package org.hycode.h2holer.client.headlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.hycode.h2holer.client.IntraContext;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.utils.CommonUtil;

import java.nio.charset.Charset;

public class IntraHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private IntraContext intraContext;
    private String data = "";

    public IntraHandler() {
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/*        intraContext.sendClient(CommonUtil.message(
                H2holerMessage.DATA, data
        ));*/
        super.channelReadComplete(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] tmp = new byte[msg.readableBytes()];
        msg.readBytes(tmp);
        //String t = msg.toString(Charset.defaultCharset());
        intraContext.sendClient(CommonUtil.message(
                H2holerMessage.DATA, tmp
        ));
    }

    public void setIntraContext(IntraContext intraContext) {
        this.intraContext = intraContext;
    }
}
