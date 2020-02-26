package org.hycode.h2holer.client.contexts;

import io.netty.channel.Channel;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;


public class IntraContext {
    private static Logger logger = LoggerFactory.getLogger(IntraContext.class);
    private final LinkedList<H2holerMessage> messageCacheList;
    private Channel channel;
    private String publicId;
    private ClientContext clientContext;
    private int status;
    private int no;

    public IntraContext() {
        messageCacheList = new LinkedList<>();
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void close() {
        this.channel.close();
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public void handleMessage(H2holerMessage h2holerMessage) {
        h2holerMessage.setNo(no);
        this.clientContext.handleMessage(h2holerMessage);
    }

    public ClientContext getClientContext() {
        return clientContext;
    }

    public void setClientContext(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    public boolean addMessageCache(H2holerMessage h2holerMessage) {
        synchronized (messageCacheList) {
            if (status == 0) {
                messageCacheList.offer(h2holerMessage);
                return true;
            } else {
                return false;
            }
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void writeAllCacheMessage() {
        synchronized (messageCacheList) {
            messageCacheList.forEach(v -> {
                channel.writeAndFlush(CommonUtil.toByteBuf(v.getData()));
            });
        }
    }
}
