package org.hycode.h2holer.server.services.contexts;

import io.netty.channel.Channel;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.server.models.H2holerClient;
import org.hycode.h2holer.server.services.workers.ClientHandleWorker;

import java.util.LinkedList;


public class ClientContext {
    private final LinkedList<PublicContext> publicContexts;
    private Channel channel;
    private ClientHandleWorker clientHandleWorker;
    private H2holerClient h2holerClient;
    public ClientContext() {
        publicContexts = new LinkedList<>();
    }

    public LinkedList<PublicContext> getPublicContexts() {
        return publicContexts;
    }

    public void addPublicContext(PublicContext publicContext) {
        publicContexts.add(publicContext);
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


    public void setClientHandleWorker(ClientHandleWorker clientHandleWorker) {
        this.clientHandleWorker = clientHandleWorker;
    }

    public void handleMessage(H2holerMessage msg) {
        this.clientHandleWorker.handleMessage(this, msg);
    }


    public void setH2holerClient(H2holerClient h2holerClient) {
        this.h2holerClient = h2holerClient;
    }

    public String getClientId() {
        return h2holerClient.getClientId();
    }
}
