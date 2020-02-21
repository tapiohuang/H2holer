package org.hycode.h2holer.server.services.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.hycode.h2holer.common.hendlers.H2holerMessageToByteEncoder;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.modles.H2holerResult;
import org.hycode.h2holer.server.models.H2holerClient;
import org.hycode.h2holer.server.services.ServiceManager;
import org.hycode.h2holer.server.services.publicly.PublicContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientServiceHandler extends SimpleChannelInboundHandler<H2holerMessage> {
    private static final Logger log = LoggerFactory.getLogger(H2holerMessageToByteEncoder.class);
    private ClientContext clientContext;
    private Channel channel;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, H2holerMessage msg) throws Exception {
        log.info("收到数据:{}", msg);
        handleMessage(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("通道激活");
        this.channel = ctx.channel();
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("通道关闭");
        clientContext.close();
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info(cause.getMessage());
    }

    private void checkAuth(H2holerMessage h2holerMessage) {
        H2holerResult<H2holerClient> result = ClientManager.getClientByAccessKey(h2holerMessage.getData());
        if (result.getCode() == H2holerResult.OK) {
            h2holerMessage.setData("鉴权成功");
            /*
            创建Context
             */
            this.initContext(result.getData());
            if (clientContext.isReady()) {
                ServiceManager.registerClient(clientContext);
            }
            h2holerMessage.setType(H2holerMessage.AUTH_SUCCESS);
        } else {
            h2holerMessage.setType(H2holerMessage.AUTH_FAIL);
        }
        this.clientContext.send(h2holerMessage);
    }

    private void initContext(H2holerClient h2holerClient) {
        ClientContext clientContext = new ClientContext();
        clientContext.setH2holerClient(h2holerClient);
        clientContext.registerChannel(channel);
        clientContext.initializer();
        this.clientContext = clientContext;
    }

    private void handleMessage(H2holerMessage h2holerMessage) {
        int type = h2holerMessage.getType();
        switch (type) {
            case H2holerMessage.AUTH:
                this.checkAuth(h2holerMessage);
                break;
            case H2holerMessage.DATA:
                clientContext.sendPublic(h2holerMessage);
                break;
            case H2holerMessage.CLIENT_PUBLIC_INIT_SUCCESS:
                PublicContext publicContext = this.clientContext.getPublicContext(h2holerMessage.getSn());
                publicContext.setClientPublicContextOK();
                break;
        }
    }

}
