package org.hycode.h2holer.server.services.client;

import io.netty.channel.Channel;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.server.models.H2holerClient;
import org.hycode.h2holer.server.models.H2holerPortMapper;
import org.hycode.h2holer.server.services.ServiceContext;
import org.hycode.h2holer.server.services.publicly.PublicContext;
import org.hycode.h2holer.server.services.publicly.PublicService;
import org.hycode.h2holer.server.utils.DaoUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class ClientContext implements ServiceContext<PublicContext> {
    private final List<Integer> ports;
    private final HashMap<Integer, H2holerPortMapper> portMapperMap;
    private final HashMap<Integer, String> protocolMap;
    private final HashMap<String, PublicContext> publicContextMap;
    private Channel channel;
    private H2holerClient h2holerClient;
    private boolean status;

    public ClientContext() {
        ports = new LinkedList<>();
        protocolMap = new HashMap<>();
        publicContextMap = new HashMap<>();
        portMapperMap = new HashMap<>();
    }

    public HashMap<Integer, H2holerPortMapper> getPortMapperMap() {
        return portMapperMap;
    }

    public List<Integer> getPorts() {
        return ports;
    }

    public void setH2holerClient(H2holerClient h2holerClient) {
        this.h2holerClient = h2holerClient;
    }

    @Override
    public void send(H2holerMessage h2holerMessage) {
        channel.writeAndFlush(h2holerMessage);
    }

    @Override
    public void registerContext(PublicContext publicContext) {
        publicContextMap.put(publicContext.getSn(), publicContext);
    }

    @Override
    public void registerChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void initializer() {
        String clientId = h2holerClient.getClientId();
        h2holerClient.setClientChannel(channel);
        List<H2holerPortMapper> h2holerPortMappers = DaoUtil.getH2holerPortMapperDao().findPortMapperByClientId(clientId);
        for (H2holerPortMapper h2holerPortMapper : h2holerPortMappers
        ) {
            int publicPort = h2holerPortMapper.getPublicPort();
            PublicService.get().listenPort(publicPort);
            ports.add(publicPort);
            portMapperMap.put(publicPort, h2holerPortMapper);
            protocolMap.put(publicPort, h2holerPortMapper.getPortType());
        }
        status = true;
    }

    @Override
    public boolean isReady() {
        return status;
    }

    public String getProtocol(int port) {
        return protocolMap.get(port);
    }

    public void sendPublic(H2holerMessage h2holerMessage) {
        String publicSn = h2holerMessage.getSn();
        PublicContext publicContext = publicContextMap.get(publicSn);
        publicContext.send(h2holerMessage);
    }

    @Override
    public void close() {
        publicContextMap.forEach((sn, publicContext) -> {
            publicContext.close();
        });
    }

    public H2holerPortMapper getPortMapper(int port) {
        return portMapperMap.get(port);
    }

    public PublicContext getPublicContext(String sn) {
        return publicContextMap.get(sn);
    }
}
