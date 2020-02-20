package org.hycode.h2holer.common.hendlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.hycode.h2holer.common.constants.H2holerConst;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class H2holerMessageToByteEncoder extends MessageToByteEncoder<H2holerMessage> {
    private static final Logger log = LoggerFactory.getLogger(H2holerMessageToByteEncoder.class);


    protected void encode(ChannelHandlerContext ctx, H2holerMessage msg, ByteBuf out) throws Exception {
        int bodyLen = 0;
        int headLen = H2holerConst.MSG_LEN +
                H2holerConst.MSG_TYPE_LEN +
                H2holerConst.MSG_SN_LEN +
                H2holerConst.MSG_NO_LEN +
                H2holerConst.MSG_DATA_LEN +
                H2holerConst.MSG_MD5_LEN;
        int dataLen = msg.getDataBytes().length;
        bodyLen = headLen + dataLen;
        byte[] snBytes = msg.getSnBytes();
        byte[] md5Bytes = msg.getMd5Bytes();
        out.writeInt(bodyLen);//记录包长度
        out.writeInt(msg.getType());//记录包类型
        out.writeBytes(snBytes);//包标识符
        out.writeInt(msg.getNo());//序号
        out.writeInt(dataLen);//内容长度
        out.writeBytes(msg.getDataBytes());//内容
        out.writeBytes(md5Bytes);//校验MD5
    }
}
