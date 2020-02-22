package org.hycode.h2holer.client.headlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.hycode.h2holer.client.IntraContext;
import org.hycode.h2holer.client.managers.ClientService;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntraHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static Logger log = LoggerFactory.getLogger(IntraHandler.class);
    private IntraContext intraContext;
    private String data = "";
    private int no = 0;

    public IntraHandler() {
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        while (msg.readableBytes() > 952) {
            byte[] tmp = new byte[952];
            msg.readBytes(tmp);
            H2holerMessage h2holerMessage = CommonUtil.message(
                    H2holerMessage.DATA, tmp, intraContext.getSn(), ClientService.getClientContext().getClientId(), no
            );
            ClientService.addClientMessage(h2holerMessage);
            no++;
        }
        byte[] tmp = new byte[msg.readableBytes()];
        msg.readBytes(tmp);
        H2holerMessage h2holerMessage = CommonUtil.message(
                H2holerMessage.DATA, tmp, intraContext.getSn(), ClientService.getClientContext().getClientId(), no
        );
        ClientService.addClientMessage(h2holerMessage);
        no++;
    }

    public void setIntraContext(IntraContext intraContext) {
        this.intraContext = intraContext;
    }
}
