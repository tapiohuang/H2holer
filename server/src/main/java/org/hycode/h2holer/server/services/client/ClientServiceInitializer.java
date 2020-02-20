package org.hycode.h2holer.server.services.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.hycode.h2holer.common.hendlers.ByteToH2holerMessageDecoder;
import org.hycode.h2holer.common.hendlers.H2holerMessageToByteEncoder;

public class ClientServiceInitializer {
    public static ChannelInitializer<NioSocketChannel> getClientServiceInitializer() {
        return new ChannelInitializer<NioSocketChannel>() {
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline ph = ch.pipeline();

                ph.addLast(new H2holerMessageToByteEncoder());
                //ph.addLast(new SNChecker());

                ph.addLast(new ByteToH2holerMessageDecoder(2 * 1024 * 1024, 0, 4, 0, 0));
                ph.addLast(new ClientServiceHandler());

            }
        };
    }

/*    public static ChannelInitializer<NioSocketChannel> getClientControlServiceInitializer() {
        return new ChannelInitializer<NioSocketChannel>() {
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline ph = ch.pipeline();

                ph.addLast(new org.hycode.adang.server.services.handlers.ADangMessageToByteEncoder());
                //ph.addLast(new SNChecker());


                ph.addLast(new org.hycode.adang.server.services.handlers.ByteToADangMessageDecoder(2 * 1024 * 1024, 0, 4, 0, 0));
                ph.addLast(new ClientControlServiceHandler());
            }
        };
    }*/
}
