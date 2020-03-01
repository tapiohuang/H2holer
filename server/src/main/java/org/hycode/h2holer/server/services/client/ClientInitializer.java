package org.hycode.h2holer.server.services.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.hycode.h2holer.common.handlers.ByteToH2holerMessageDecoder;
import org.hycode.h2holer.common.handlers.H2holerMessageToByteEncoder;

public class ClientInitializer {
    public static ChannelInitializer<NioSocketChannel> getClientInitializer() {
        return new ChannelInitializer<NioSocketChannel>() {
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline ph = ch.pipeline();
                ph.addLast(new H2holerMessageToByteEncoder());

                ph.addLast(new ByteToH2holerMessageDecoder(1024));
                ph.addLast(new ClientHandler());

            }
        };
    }
}
