package org.hycode.h2holer.server.services;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.hycode.h2holer.server.services.client.ClientService;
import org.hycode.h2holer.server.services.publicly.PublicServiceInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public abstract class AbstractService {
    private static Logger logger = LoggerFactory.getLogger(AbstractService.class);


    protected ServerBootstrap serverBootstrap;
    protected NioEventLoopGroup serverWorker;
    protected NioEventLoopGroup serverBoss;
    protected final HashMap<Integer, Channel> boundPortMap;

    protected AbstractService() {
        boundPortMap = new HashMap<>();
    }


    public void start(ChannelInitializer<NioSocketChannel> channelChannelInitializer) {
        serverBootstrap = new ServerBootstrap();
        serverBoss = new NioEventLoopGroup();
        serverWorker = new NioEventLoopGroup();
        serverBootstrap.group(serverBoss, serverWorker)
                .channel(NioServerSocketChannel.class)
                .childHandler(channelChannelInitializer);
    }

    public void listenPort(int port) {
        Channel channel = boundPortMap.get(port);
        if (channel != null) {
            if (channel.isActive() && channel.isOpen()) {
                logger.info("端口：[{}] 已经绑定", port);
            }
        }
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            if (channelFuture != null && channelFuture.isSuccess()) {
                channel = channelFuture.channel();
                boundPortMap.put(port, channel);
                logger.info("绑定端口：[{}] 成功", port);
            } else {
                logger.info("绑定端口：[{}] 失败", port);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.info("绑定端口：[{}] 失败，{}", port, e);
        }

    }
}
