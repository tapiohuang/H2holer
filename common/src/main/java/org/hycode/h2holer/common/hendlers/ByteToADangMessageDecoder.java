package org.hycode.adang.server.services.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.hycode.adang.server.ADangServerApplication;
import org.hycode.adang.server.constants.ADangConst;
import org.hycode.adang.server.models.AdangMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteToADangMessageDecoder extends LengthFieldBasedFrameDecoder {
    private static Logger logger = LoggerFactory.getLogger(ADangServerApplication.class);

    public ByteToADangMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        //System.out.println(in.toString(Charset.defaultCharset()));
        if (in == null) {
            return null;
        }
        if (in.readableBytes() < 4) {
            return null;
        }
        int frameLen = in.readInt();//获取总的数据长度
        frameLen = frameLen - ADangConst.ADANG_MSG_DATA_LEN;
        if (in.readableBytes() < frameLen) {
            in.resetReaderIndex();
            return null;
        }
        /*
        包类型
         */
        AdangMsg adangMsg = new AdangMsg();
        int type = in.readInt();
        adangMsg.setType(type);

        /*
        包标识符
         */
        byte[] snBytes = new byte[ADangConst.ADANG_MSG_SN_LEN];
        in.readBytes(snBytes);
        adangMsg.setSn(snBytes);

        /*
        包序号
         */
        int no = in.readInt();
        adangMsg.setNo(no);

        /*
        内容长度
         */
        int dataLen = in.readInt();

        /*
        内容
         */
        byte[] dataBytes = new byte[dataLen];
        in.readBytes(dataBytes);
        adangMsg.setData(dataBytes);

        /*
        校验MD5
         */
        byte[] md5Bytes = new byte[ADangConst.ADANG_MSG_MD5_LEN];
        in.readBytes(md5Bytes);
        adangMsg.setMd5(md5Bytes);

        return adangMsg;
    }

}
