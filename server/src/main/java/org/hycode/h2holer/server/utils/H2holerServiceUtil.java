package org.hycode.h2holer.server.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;

public class H2holerServiceUtil {
    public static Integer port(Channel channel) {
        if (null == channel) {
            return null;
        } else {
            InetSocketAddress addr = (InetSocketAddress) channel.localAddress();
            return addr.getPort();
        }
    }

    public static boolean isActive(Channel channel) {
        if (channel == null) {
            return false;
        }
        return channel.isActive() || channel.isOpen();
    }

    public static ByteBuf toByteBuf(Object o) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(o.toString().getBytes());
        return byteBuf;
    }
}
