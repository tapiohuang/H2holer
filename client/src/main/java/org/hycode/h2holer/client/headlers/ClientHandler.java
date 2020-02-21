package org.hycode.h2holer.client.headlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.hycode.h2holer.client.ClientContext;
import org.hycode.h2holer.client.managers.ClientManager;
import org.hycode.h2holer.client.managers.IntraManager;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.modles.H2holerPublicConfig;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler extends SimpleChannelInboundHandler<H2holerMessage> {
    private static Logger log = LoggerFactory.getLogger(ClientHandler.class);

    private final ClientContext clientContext;

    public ClientHandler() {
        clientContext = ClientManager.getClientContext();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        clientContext.close();
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("错误:{}", cause.getMessage());
        //super.exceptionCaught(ctx, cause);
    }

    protected void channelRead0(ChannelHandlerContext ctx, H2holerMessage msg) throws Exception {
        handleMessage(msg);
    }


    private void handleMessage(H2holerMessage h2holerMessage) {
        int type = h2holerMessage.getType();
        switch (type) {
            case H2holerMessage.AUTH_SUCCESS:
                log.info("鉴权成功");
                break;
            case H2holerMessage.AUTH_FAIL:
                log.info("鉴权失败");
                clientContext.close();
                break;
            case H2holerMessage.CLIENT_PUBLIC_INIT:
                initIntraContext(h2holerMessage);
                break;
            case H2holerMessage.DATA:
                clientContext.sendIntra(h2holerMessage);
                break;
            case H2holerMessage.CLIENT_PUBLIC_OFF:
                clientContext.closeIntra(h2holerMessage);
                break;
        }
    }

    private void initIntraContext(H2holerMessage h2holerMessage) {
        String json = h2holerMessage.getDataString();
        H2holerPublicConfig h2holerPublicConfig = CommonUtil.fromJson(json, H2holerPublicConfig.class);
        IntraManager.connectIntra(h2holerPublicConfig);
    }

}
