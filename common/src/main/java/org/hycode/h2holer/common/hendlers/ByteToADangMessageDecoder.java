package org.hycode.h2holer.common.hendlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import org.hycode.h2holer.common.constants.H2holerConst;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteToADangMessageDecoder extends LengthFieldBasedFrameDecoder {
    private static Logger logger = LoggerFactory.getLogger(ByteToADangMessageDecoder.class);

    public ByteToADangMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in == null) {
            return null;
        }
        if (in.readableBytes() < 4) {
            return null;
        }
        int frameLen = in.readInt();//获取总的数据长度
        frameLen = frameLen - H2holerConst.MSG_LEN;
        if (in.readableBytes() < frameLen) {
            in.resetReaderIndex();
            return null;
        }
        /*
        包类型
         */
        H2holerMessage h2holerMessage = new H2holerMessage();
        int type = in.readInt();
        h2holerMessage.setType(type);

        /*
        包标识符
         */
        byte[] snBytes = new byte[H2holerConst.MSG_SN_LEN];
        in.readBytes(snBytes);
        h2holerMessage.setSn(snBytes);

        /*
        包序号
         */
        int no = in.readInt();
        h2holerMessage.setNo(no);

        /*
        内容长度
         */
        int dataLen = in.readInt();

        /*
        内容
         */
        byte[] dataBytes = new byte[dataLen];
        in.readBytes(dataBytes);
        h2holerMessage.setData(dataBytes);

        /*
        校验MD5
         */
        byte[] md5Bytes = new byte[H2holerConst.MSG_MD5_LEN];
        in.readBytes(md5Bytes);
        h2holerMessage.setMd5(md5Bytes);

        return h2holerMessage;
    }

}
