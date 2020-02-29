package org.hycode.h2holer.common.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ByteToH2holerMessageDecoder extends FixedLengthFrameDecoder {
    private static Logger logger = LoggerFactory.getLogger(ByteToH2holerMessageDecoder.class);

    /**
     * Creates a new instance.
     *
     * @param frameLength the length of the frame
     */
    public ByteToH2holerMessageDecoder(int frameLength) {
        super(frameLength);
    }


    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in == null) {
            return null;
        }

        H2holerMessage h2holerMessage = new H2holerMessage();
        /*
        包类型
         */
        int type = in.readInt();
        h2holerMessage.setType(type);

        /*
        包标识符
         */
        byte[] snBytes = new byte[32];
        in.readBytes(snBytes);
        h2holerMessage.setSn(new String(snBytes));

        /*
        Client标识符
         */
        byte[] clientBytes = new byte[32];
        in.readBytes(clientBytes);
        h2holerMessage.setClientId(new String(clientBytes));

        /*
        包类型
         */
        int no = in.readInt();
        h2holerMessage.setNo(no);

        /*
        数据长度
         */
        int dataLen = in.readInt();

        /*
        数据本体
        */
        byte[] dataBytes = new byte[948];
        in.readBytes(dataBytes);
        byte[] lastData = Arrays.copyOf(dataBytes, dataLen);
        h2holerMessage.setData(lastData);

        return h2holerMessage;
    }

}
