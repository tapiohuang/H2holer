package org.hycode.h2holer.server.services.publicly;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublicServiceDispatch extends SimpleChannelInboundHandler<ByteBuf> {

    private static final Logger log = LoggerFactory.getLogger(PublicServiceDispatch.class);
    private PublicContext publicContext;
    private Channel channel;

    public PublicServiceDispatch() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        publicContext.send(CommonUtil.message(H2holerMessage.UN_CONNECTED, "Dispatch Fail"));
        publicContext.close();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("分发器启动:");
        try {
            channel = ctx.channel();
            this.initContext();
            if (this.publicContext.isReady()) {
                String protocol = this.publicContext.getProtocol();
                switch (protocol) {
                    case "HTTP":
                        log.info("分发到:HttpServiceHandler");
                        ctx.pipeline().addLast(new HttpServiceHandler(publicContext));
                        ctx.pipeline().remove(this);
                        ctx.fireChannelActive();
                        break;
                }

            } else {
                log.info("客户端未连接");
            }
            super.channelActive(ctx);
        } catch (Throwable t) {
            log.info("错误:{}", t.getMessage());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    private void initContext() {
        publicContext = new PublicContext();
        publicContext.registerChannel(channel);
        publicContext.initializer();
    }
}
