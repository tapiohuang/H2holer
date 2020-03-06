package org.hycode.h2holer.server.services.managers;

import io.netty.channel.Channel;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.hycode.h2holer.server.services.contexts.ClientContext;
import org.hycode.h2holer.server.services.contexts.PublicContext;
import org.hycode.h2holer.server.utils.H2holerServiceUtil;

import java.util.HashMap;

public class PublicManager {
    private static PublicManager publicManager;
    //private final HashMap<Channel, PublicContext> publicContextMap;
    private final HashMap<String, PublicContext> publicIdMap;

    private PublicManager() {
        //publicContextMap = new HashMap<>();
        publicIdMap = new HashMap<>();
    }

    public static PublicManager get() {
        if (publicManager == null) {
            publicManager = new PublicManager();
        }
        return publicManager;
    }

    public PublicContext applyPublicContext(Channel channel) {
        PublicContext publicContext = new PublicContext();
        String publicId = CommonUtil.randomID();
        int port = H2holerServiceUtil.port(channel);
        String clientId = ServerManager.get().searchClientId(port);
        ClientContext clientContext = ClientManager.get().searchClientContext(clientId);
        clientContext.addPublicContext(publicContext);

        publicContext.setPublicId(publicId);
        publicContext.setChannel(channel);
        publicContext.setClientContext(clientContext);
        publicContext.setH2holerPortMapper(ServerManager.get().searchPortMapper(port));
        
        publicIdMap.put(publicId, publicContext);
        return publicContext;
    }

    public void destroyPublicContext(String publicId) {
        PublicContext publicContext = getPublicContext(publicId);
        if (publicContext == null) {
            return;
        }
        destroyPublicContext(publicContext);
    }

    public void destroyPublicContext(PublicContext publicContext) {
        publicContext.close();
        publicIdMap.remove(publicContext.getPublicId());
    }

    public PublicContext getPublicContext(String publicId) {
        return publicIdMap.get(publicId);
    }
}
