package org.hycode.h2holer.server.services;

import io.netty.channel.Channel;
import org.hycode.h2holer.common.modles.H2holerMessage;


public interface ServiceContext<C extends ServiceContext> {
    void send(H2holerMessage h2holerMessage);

    void registerContext(C serviceContext);

    void registerChannel(Channel channel);

    void initializer();

    boolean isReady();

    void close();

}
