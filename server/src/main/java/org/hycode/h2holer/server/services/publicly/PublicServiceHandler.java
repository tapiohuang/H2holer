package org.hycode.h2holer.server.services.publicly;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.modles.H2holerResult;
import org.hycode.h2holer.server.models.H2holerClient;
import org.hycode.h2holer.server.utils.H2holerServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class PublicServiceHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static final Logger log = LoggerFactory.getLogger(PublicServiceHandler.class);

    public PublicServiceHandler() {

    }

    private H2holerClient h2holerClient;
    private int publicPortNum;
    private String sn;
    private Channel channel;
    private int no = 0;

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("读取完毕");
        System.out.println(data);
        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
        String str = sdf.format(cd.getTime());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HTTP/1.1 200 OK\r\n");
        stringBuilder.append("Content-Type:application/json;charset=utf-8\r\n");
        stringBuilder.append("Connection:keep-alive\r\n");
        stringBuilder.append("Content-Length:77\r\n");
        stringBuilder.append("\r\n");
        String json = "{\"code\":-1,\"msg\":\"非法请求\",\"data\":\"\",\"time\":\"2020-02-19 15:41:15\"}";
        stringBuilder.append(json);
        ByteBuf buffer = Unpooled.copiedBuffer(stringBuilder.toString().getBytes(Charset.defaultCharset()));
        //ctx.writeAndFlush(buffer);
        super.channelReadComplete(ctx);
    }

    String data = "";

    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        int len = msg.readableBytes();
        byte[] bytes = new byte[len];
        msg.readBytes(bytes);
        String s = new String(bytes);
        System.out.println(s);
        data += s;
        //ctx.close();
/*        if (h2holerClient != null) {
            log.info("客户端已连接：" + publicPortNum);
            //sendDataToClient(H2holerMessage.DATA_BODY, new String(bytes));
        } else {
            log.info("客户端未连接：" + publicPortNum);
        }*/
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("通道激活");
        channel = ctx.channel();
        publicPortNum = H2holerServiceUtil.port(channel);//获取端口号
        log.info("获取监听端口号：" + publicPortNum);
        //根据端口号寻找转发的channel
        //this.aDangClient = ADangPublicManager.get().getADangClient(publicPortNum);
        //sn = AppUtil.randomID36();//为连接分配标识符
        //ADangPublicManager.get().registerPublicChannel(sn, channel);//注册PublicChannel
        //sendDataToClient(H2holerMessage.DATA_START, "start");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //ADangPublicManager.get().unregisterPublicChannel(sn);//注册PublicChannel
        //sendDataToClient(H2holerMessage.DATA_END, "end");
        log.info("通道关闭");
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    public void sendDataToClient(int type, String data) {

        H2holerMessage h2holerMessage = new H2holerMessage();
        h2holerMessage.setType(type);
        h2holerMessage.setSn(this.sn);
        h2holerMessage.setData(data);
        h2holerMessage.setNo(no);

        //ADangClientManager.get().sendData(aDangClient, adangMsg);

        no++;
    }
}
