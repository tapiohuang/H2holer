package org.hycode.h2holer.server.services.publicly;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.modles.H2holerPort;
import org.hycode.h2holer.common.modles.H2holerResult;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.hycode.h2holer.server.services.ServiceContext;
import org.hycode.h2holer.server.services.ServiceManager;
import org.hycode.h2holer.server.services.client.ClientContext;
import org.hycode.h2holer.server.utils.H2holerServiceUtil;

public class PublicContext implements ServiceContext<ClientContext> {
    private ClientContext clientContext;
    private Channel channel;
    private String sn;
    private int publicNo;
    private int clientNo;
    private H2holerPort h2holerPort;
    private boolean status;

    @Override
    public void send(H2holerMessage h2holerMessage) {
        //h2holerMessage.setNo(no);
        h2holerMessage.setSn(sn);
        ByteBuf byteBuf = Unpooled.copiedBuffer(h2holerMessage.toString().getBytes());
        channel.writeAndFlush(byteBuf);
        //no++;
    }

    @Override
    public void registerContext(ClientContext clientContext) {
        this.clientContext = clientContext;
        clientContext.registerContext(this);
    }

    @Override
    public void registerChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void initializer() {
        this.sn = CommonUtil.randomID();
        int port = H2holerServiceUtil.port(channel);
        H2holerResult<ClientContext> result = ServiceManager.getClientContextByPort(port);
        if (result.getCode() == H2holerResult.OK) {
            clientContext = result.getData();
            clientContext.registerContext(this);
            String protocol = clientContext.getProtocol(port);
            this.h2holerPort = new H2holerPort(port, protocol);
            status = true;
        } else {
            send(CommonUtil.message(H2holerMessage.UN_CONNECTED, result.getMsg()));
        }
    }

    public String getProtocol() {
        return h2holerPort.getType();
    }

    @Override
    public boolean isReady() {
        return status;
    }

    @Override
    public void close() {
        channel.close();
    }

    public void sendClient(H2holerMessage h2holerMessage) {
        h2holerMessage.setSn(sn);
        h2holerMessage.setNo(clientNo);
        clientContext.send(h2holerMessage);
        clientNo++;
    }

    public String getSn() {
        return sn;
    }
}
