package org.hycode.h2holer.client.handlers;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.hycode.h2holer.client.contexts.IntraContext;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntraHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static Logger log = LoggerFactory.getLogger(IntraHandler.class);
    private IntraContext intraContext;

    public IntraHandler() {
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("关闭连接：{}", intraContext.getPublicId());
        H2holerMessage h2holerMessage = CommonUtil.message(H2holerMessage.INTRA_CLOSE, "目标关闭连接", intraContext.getPublicId(), intraContext.getClientContext().getClientId(), 0);
        intraContext.handleMessage(h2holerMessage);
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        try {
            boolean run = true;
            while (run) {
                int a = msg.readableBytes();
                byte[] tmp;
                if (a > 948) {
                    tmp = new byte[948];
                } else {
                    tmp = new byte[a];
                    run = false;
                }
                msg.readBytes(tmp);
                //System.out.println(new String(tmp));
                H2holerMessage h2holerMessage = CommonUtil.message(H2holerMessage.INTRA_RETURN_DATA, tmp, intraContext.getPublicId(), intraContext.getClientContext().getClientId(), 0);
                intraContext.handleMessage(h2holerMessage);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

    public void setIntraContext(IntraContext intraContext) {
        this.intraContext = intraContext;
    }


}
