package org.hycode.h2holer.client.linsteners;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.hycode.h2holer.client.contexts.ClientContext;
import org.hycode.h2holer.client.managers.ClientManager;
import org.hycode.h2holer.client.utils.ClientUtil;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientAuthListener implements ChannelFutureListener {
    private static Logger logger = LoggerFactory.getLogger(ClientAuthListener.class);

    public ClientAuthListener() {
    }

    public void operationComplete(ChannelFuture future) throws Exception {
        if (!future.isSuccess()) {
            logger.info("连接服务器失败");
            ClientUtil.exit();
        } else {
            Channel clientChannel = future.channel();
            ClientContext clientContext = ClientManager.get().applyClientContext(clientChannel);
            logger.info("连接服务器成功");
            H2holerMessage h2holerMessage = CommonUtil.message(H2holerMessage.AUTH, "36f63b623c374e6da9b20d2d59a24b8b", CommonUtil.randomID(), CommonUtil.randomID(), 0);
            clientContext.handleMessage(h2holerMessage);
        }
    }
}
