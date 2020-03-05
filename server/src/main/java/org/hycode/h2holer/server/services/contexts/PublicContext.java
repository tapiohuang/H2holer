package org.hycode.h2holer.server.services.contexts;

import io.netty.channel.Channel;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.hycode.h2holer.server.models.H2holerPortMapper;


public class PublicContext {
    private ClientContext clientContext;
    private Channel channel;
    private String publicId;
    private H2holerPortMapper h2holerPortMapper;

    public PublicContext() {
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }


    public void close() {
        channel.close();
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public ClientContext getClientContext() {
        return clientContext;
    }

    public void setClientContext(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    public String getClientId() {
        return clientContext.getClientId();
    }

    public void handleMessage(H2holerMessage h2holerMessage) {
        clientContext.handleMessage(h2holerMessage);
    }

    public H2holerPortMapper getH2holerPortMapper() {
        return h2holerPortMapper;
    }

    public void setH2holerPortMapper(H2holerPortMapper h2holerPortMapper) {
        this.h2holerPortMapper = h2holerPortMapper;
    }
}
