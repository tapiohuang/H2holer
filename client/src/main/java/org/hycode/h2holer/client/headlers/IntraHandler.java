package org.hycode.h2holer.client.headlers;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.hycode.h2holer.client.IntraContext;
import org.hycode.h2holer.client.managers.ClientService;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntraHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static Logger log = LoggerFactory.getLogger(IntraHandler.class);
    private IntraContext intraContext;
    private String data = "";
    private int no = 0;
    private int size = 0;

    public IntraHandler() {
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("关闭连接：{}",intraContext.getSn());
        H2holerMessage h2holerMessage = CommonUtil.message(H2holerMessage.INTRA_CLOSE, "目标关闭连接", intraContext.getSn(), ClientService.getClientContext().getClientId(), no);
        ClientService.addClientMessage(h2holerMessage);
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        boolean run = true;
        while (run) {
            int a = msg.readableBytes();
            byte[] tmp;
            if (a > 948) {
                tmp = new byte[948];
            } else {
                tmp = new byte[a];
                run = false;
            }
            msg.readBytes(tmp);
            H2holerMessage h2holerMessage = CommonUtil.message(H2holerMessage.INTRA_RETURN_DATA, tmp, intraContext.getSn(), ClientService.getClientContext().getClientId(), no);
            ClientService.addClientMessage(h2holerMessage);
            System.out.println(new String(h2holerMessage.getData()));
            no++;
        }
    }

    public void setIntraContext(IntraContext intraContext) {
        this.intraContext = intraContext;
    }


}
