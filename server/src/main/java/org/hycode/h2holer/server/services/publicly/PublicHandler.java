package org.hycode.h2holer.server.services.publicly;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.modles.H2holerPublicConfig;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.hycode.h2holer.server.models.H2holerPortMapper;
import org.hycode.h2holer.server.services.contexts.PublicContext;
import org.hycode.h2holer.server.services.managers.PublicManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublicHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static final Logger log = LoggerFactory.getLogger(PublicHandler.class);
    private PublicContext publicContext;
    private Channel channel;

    public PublicHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
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
            H2holerMessage h2holerMessage = CommonUtil.message(H2holerMessage.INTRA_DATA, tmp, publicContext.getPublicId(), publicContext.getClientId(), 0);
            publicContext.handleMessage(h2holerMessage);
        }
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.channel = ctx.channel();
        publicContext = PublicManager.get().applyPublicContext(channel);
        H2holerPortMapper h2holerPortMapper = publicContext.getH2holerPortMapper();
        H2holerPublicConfig h2holerPublicConfig = new H2holerPublicConfig(
                h2holerPortMapper.getPortType(), h2holerPortMapper.getInnerAddr(), h2holerPortMapper.getInnerPort(), publicContext.getPublicId()
        );
        H2holerMessage h2holerMessage = CommonUtil.message(H2holerMessage.INTRA_INIT, h2holerPublicConfig.toJson(), publicContext.getPublicId(), publicContext.getClientId(), 0);
        log.info("对外管道激活:{}", publicContext.getPublicId());
        publicContext.handleMessage(h2holerMessage);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("对外管道关闭:{}", publicContext.getPublicId());
        H2holerMessage h2holerMessage = CommonUtil.message(H2holerMessage.INTRA_DISCONNECT, "disconnect", publicContext.getPublicId(), publicContext.getClientId(), 0);
        publicContext.handleMessage(h2holerMessage);
        PublicManager.get().destroyPublicContext(publicContext.getPublicId());
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

}
