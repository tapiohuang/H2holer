/*
 * Copyright 2018-present, Yudong (Dom) Wang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.hycode.h2holer.client.inits;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.hycode.h2holer.client.headlers.ClientHandler;
import org.hycode.h2holer.common.hendlers.ByteToH2holerMessageDecoder;
import org.hycode.h2holer.common.hendlers.H2holerMessageToByteEncoder;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new H2holerMessageToByteEncoder());

        ch.pipeline().addLast(new ByteToH2holerMessageDecoder(1024));
        ch.pipeline().addLast(new ClientHandler());
    }

}
