package org.hycode.h2holer.client.headlers;

import org.hycode.h2holer.client.ClientContext;
import org.hycode.h2holer.client.managers.ClientService;
import org.hycode.h2holer.client.managers.IntraService;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.modles.H2holerPublicConfig;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientMessageHandler {
    private static Logger log = LoggerFactory.getLogger(ClientHandler.class);
    private final ClientContext clientContext;

    public ClientMessageHandler() {
        clientContext = ClientService.getClientContext();
    }

    public void handle(H2holerMessage h2holerMessage) {
        try {
            int type = h2holerMessage.getType();
            switch (type) {
                case H2holerMessage.AUTH_SUCCESS:
                    log.info("鉴权成功");
                    clientContext.setClientId(h2holerMessage.getClientId());
                    break;
                case H2holerMessage.AUTH_FAIL:
                    log.info("鉴权失败");
                    break;
                case H2holerMessage.CLIENT_PUBLIC_INIT:
                    initIntraContext(h2holerMessage);
                    break;
                case H2holerMessage.DATA:
                    IntraService.addMessage(h2holerMessage);
                    break;
                case H2holerMessage.CLIENT_PUBLIC_OFF:
                    IntraService.closeIntra(h2holerMessage);
                    break;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }


    private void initIntraContext(H2holerMessage h2holerMessage) {
        String json = new String(h2holerMessage.getData());
        H2holerPublicConfig h2holerPublicConfig = CommonUtil.fromJson(json, H2holerPublicConfig.class);
        IntraService.connectIntra(h2holerPublicConfig);
    }
}
