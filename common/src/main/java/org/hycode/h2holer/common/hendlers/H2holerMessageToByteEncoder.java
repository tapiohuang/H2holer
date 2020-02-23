package org.hycode.h2holer.common.hendlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class H2holerMessageToByteEncoder extends MessageToByteEncoder<H2holerMessage> {
    private static final Logger log = LoggerFactory.getLogger(H2holerMessageToByteEncoder.class);


    protected void encode(ChannelHandlerContext ctx, H2holerMessage msg, ByteBuf out) throws Exception {
        int dataLen = msg.getData().length;
        byte[] sn = Arrays.copyOf(msg.getSn().getBytes(), 32);
        byte[] clientId = Arrays.copyOf(msg.getClientId().getBytes(), 32);
        byte[] lastData = Arrays.copyOf(msg.getData(), 948);
        int type = msg.getType();
        int no = msg.getNo();

        out.writeInt(type);
        out.writeBytes(sn);
        out.writeBytes(clientId);
        out.writeInt(no);
        out.writeInt(dataLen);
        out.writeBytes(lastData);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("出错了:{}", cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }
}
