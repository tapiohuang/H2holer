package org.hycode.h2holer.client.linsteners;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.hycode.h2holer.client.ClientContext;
import org.hycode.h2holer.client.IntraContext;
import org.hycode.h2holer.client.headlers.IntraHandler;
import org.hycode.h2holer.client.managers.ClientManager;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.modles.H2holerPublicConfig;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntraConnectListener implements ChannelFutureListener {
    private static Logger log = LoggerFactory.getLogger(IntraConnectListener.class);

    private final H2holerPublicConfig h2holerPublicConfig;

    private final ClientContext clientContext;

    public IntraConnectListener(H2holerPublicConfig h2holerPublicConfig) {
        this.h2holerPublicConfig = h2holerPublicConfig;
        this.clientContext = ClientManager.getClientContext();
    }

    public void operationComplete(ChannelFuture future) throws Exception {
        if (!future.isSuccess()) {
            log.info("连接目标服务器失败");
        } else {
            log.info("连接目标服务器成功");
            Channel channel = future.channel();
            IntraHandler intraHandler = channel.pipeline().get(IntraHandler.class);
            IntraContext intraContext = new IntraContext();
            intraContext.registerChannel(channel);
            intraContext.setSn(h2holerPublicConfig.getSn());
            intraHandler.setIntraContext(intraContext);
            this.clientContext.registerIntraContext(intraContext);
            H2holerMessage h2holerMessage = CommonUtil.message(H2holerMessage.CLIENT_PUBLIC_INIT_SUCCESS, "目标连接初始化成功");
            h2holerMessage.setSn(intraContext.getSn());
            this.clientContext.send(h2holerMessage);

        }
    }
}
