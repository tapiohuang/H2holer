package org.hycode.h2holer.client.utils;

import io.netty.channel.Channel;

public class ClientUtil {
    public static void exit() {
        try {
            Thread.sleep(1000);
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static boolean isActive(Channel channel) {
        if (channel == null) {
            return false;
        }
        return channel.isActive() || channel.isOpen();
    }
}
