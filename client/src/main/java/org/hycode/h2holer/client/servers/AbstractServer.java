package org.hycode.h2holer.client.servers;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

abstract class AbstractServer {
    protected final Bootstrap bootstrap;
    protected final NioEventLoopGroup group;

    public AbstractServer() {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
    }

    public abstract void init();

}
