package org.hycode.h2holer.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.hycode.h2holer.client.inits.ClientInitializer;
import org.hycode.h2holer.client.linsteners.ClientAuthListener;


public class H2holerClientApplication {
    public static void main(String[] args) {
        Bootstrap client = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        client.group(group);
        client.channel(NioSocketChannel.class);
        client.handler(new ClientInitializer());
        client.connect("127.0.0.1", 10000).addListener(new ClientAuthListener());
    }
}
