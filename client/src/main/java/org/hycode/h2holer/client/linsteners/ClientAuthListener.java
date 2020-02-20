package org.hycode.h2holer.client.linsteners;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.CharsetUtil;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ClientAuthListener implements ChannelFutureListener {
    private static Logger logger = LoggerFactory.getLogger(ClientAuthListener.class);

    public void operationComplete(ChannelFuture future) throws Exception {
        if (!future.isSuccess()) {
            System.out.println("连接服务器失败");
        } else {
            Channel clientChannel = future.channel();
            System.out.println("连接服务器成功");
            H2holerMessage h2holerMessage = new H2holerMessage();
            h2holerMessage.setType(H2holerMessage.AUTH);
            h2holerMessage.setData("36f63b623c374e6da9b20d2d59a24b8b".getBytes(CharsetUtil.UTF_8));
            h2holerMessage.setSn(CommonUtil.randomID());
            clientChannel.writeAndFlush(h2holerMessage);
        }
    }
}
