package org.hycode.h2holer.client.linsteners;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.hycode.h2holer.client.contexts.IntraContext;
import org.hycode.h2holer.client.handlers.IntraHandler;
import org.hycode.h2holer.client.managers.IntraManager;
import org.hycode.h2holer.common.modles.H2holerPublicConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntraConnectListener implements ChannelFutureListener {
    private static Logger log = LoggerFactory.getLogger(IntraConnectListener.class);

    private final H2holerPublicConfig h2holerPublicConfig;

    public IntraConnectListener(H2holerPublicConfig h2holerPublicConfig) {
        this.h2holerPublicConfig = h2holerPublicConfig;
    }

    public void operationComplete(ChannelFuture future) throws Exception {
        if (!future.isSuccess()) {
            log.info("连接目标服务器失败");
        } else {
            log.info("连接目标服务器成功:{}", h2holerPublicConfig.getSn());
            Channel channel = future.channel();
            IntraHandler intraHandler = channel.pipeline().get(IntraHandler.class);
            IntraContext intraContext = IntraManager.get().getIntraContext(h2holerPublicConfig.getSn());
            intraContext.setChannel(channel);
            intraHandler.setIntraContext(intraContext);
            intraContext.setStatus(1);
            intraContext.writeAllCacheMessage();
        }
    }
}
