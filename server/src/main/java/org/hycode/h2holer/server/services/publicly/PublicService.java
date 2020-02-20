package org.hycode.h2holer.server.services.publicly;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.hycode.h2holer.server.services.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


public class PublicService extends AbstractService {
    private static final PublicService publicService = new PublicService();

    public static PublicService get() {
        return publicService;
    }

    private ServerBootstrap serverBootstrap;
    private NioEventLoopGroup serverWorker;
    private NioEventLoopGroup serverBoss;
    private final HashMap<Integer, Channel> boundPortMap;


    private PublicService() {
        boundPortMap = new HashMap<>();
    }





}
