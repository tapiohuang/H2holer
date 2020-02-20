package org.hycode.h2holer.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.AbstractEventLoopGroup;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


public class H2holerClientApplication {
    public static void main(String[] args) {
        Bootstrap client = new Bootstrap();

        //第1步 定义线程组，处理读写和链接事件，没有了accept事件
        AbstractEventLoopGroup group = new NioEventLoopGroup();
        client.group(group);

        //第2步 绑定客户端通道
        client.channel(NioSocketChannel.class);

        //new HttpObjectAggregator();
        //第3步 给NIoSocketChannel初始化handler， 处理读写事件
        client.handler(new ChannelInitializer<NioSocketChannel>() {  //通道是NioSocketChannel
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ADangMessageToByteEncoder());
                //ch.pipeline().addLast(new SNChecker());


                ch.pipeline().addLast(new ByteToADangMessageDecoder(2 * 1024 * 1024, 0, 4, 0, 0));
                ch.pipeline().addLast(new SimpleClientHandler());
            }
        });
        ChannelFuture channelFuture = null;
        //连接服务器
        try {
            channelFuture = client.connect("127.0.0.1", 10000).sync().addListener(new ClientAuthListener());
            //Thread.sleep(1000);
            //new Thread(new Worker(channelFuture.channel())).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
