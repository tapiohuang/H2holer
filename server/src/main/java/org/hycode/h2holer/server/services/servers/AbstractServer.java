package org.hycode.h2holer.server.services.servers;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public abstract class AbstractServer {
    private static Logger logger = LoggerFactory.getLogger(AbstractServer.class);
    protected final HashMap<Integer, Channel> boundPortMap;
    protected ServerBootstrap serverBootstrap;
    protected NioEventLoopGroup serverWorker;
    protected NioEventLoopGroup serverBoss;

    protected AbstractServer() {
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
            ChannelFuture channelFuture = serverBootstrap.bind(port);
            channelFuture = channelFuture.sync();
            if (channelFuture != null && channelFuture.isSuccess()) {
                channel = channelFuture.channel();
                boundPortMap.put(port, channel);
                logger.info("绑定端口：[{}] 成功", port);
            } else {
                logger.info("绑定端口：[{}] 失败", port);
            }
        } catch (Throwable e) {
            logger.info("绑定端口：[{}] 失败，{}", port, e);
        }

    }
}
