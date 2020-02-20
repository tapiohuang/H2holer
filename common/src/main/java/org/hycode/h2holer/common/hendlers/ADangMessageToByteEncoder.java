package org.hycode.adang.server.services.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.hycode.adang.server.constants.ADangConst;
import org.hycode.adang.server.models.AdangMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ADangMessageToByteEncoder extends MessageToByteEncoder<AdangMsg> {
    private static final Logger log = LoggerFactory.getLogger(ADangMessageToByteEncoder.class);


    protected void encode(ChannelHandlerContext ctx, AdangMsg msg, ByteBuf out) throws Exception {

        int bodyLen = 0;
        int headLen = ADangConst.ADANG_MSG_TOTAL_LEN +
                ADangConst.ADANG_MSG_TYPE_LEN +
                ADangConst.ADANG_MSG_SN_LEN +
                ADangConst.ADANG_MSG_NO_LEN +
                ADangConst.ADANG_MSG_DATA_LEN +
                ADangConst.ADANG_MSG_MD5_LEN;
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
        //System.out.println(out.toString(Charset.defaultCharset()));
    }
}
