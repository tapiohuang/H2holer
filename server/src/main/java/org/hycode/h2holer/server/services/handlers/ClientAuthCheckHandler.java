package org.hycode.h2holer.server.services.handlers;

import io.netty.channel.Channel;
import org.hycode.h2holer.common.handlers.MessageHandler;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.modles.H2holerResult;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.hycode.h2holer.server.models.H2holerClient;
import org.hycode.h2holer.server.models.H2holerPortMapper;
import org.hycode.h2holer.server.services.contexts.ClientContext;
import org.hycode.h2holer.server.services.managers.ClientManager;
import org.hycode.h2holer.server.services.managers.ServerManager;
import org.hycode.h2holer.server.services.workers.ClientHandleWorker;
import org.hycode.h2holer.server.utils.DaoUtil;

import java.util.List;
import java.util.Map;


public class ClientAuthCheckHandler implements MessageHandler<ClientHandleWorker.Entry> {

    @Override
    public boolean onMessage(ClientHandleWorker.Entry msgEntry) {
        return msgEntry.getH2holerMessage().getType() == H2holerMessage.AUTH;
    }

    @Override
    public void handleMessage(ClientHandleWorker.Entry msgEntry) {
        H2holerMessage h2holerMessage = msgEntry.getH2holerMessage();
        ClientContext clientContext = msgEntry.getClientContext();
        Channel channel = clientContext.getChannel();
        String accessKey = new String(h2holerMessage.getData());
        H2holerResult<H2holerClient> result = getClientByAccessKey(accessKey);
        H2holerMessage resultMessage;
        if (result.getCode() == H2holerResult.OK) {
            this.initContext(clientContext, result.getData());
            resultMessage = CommonUtil.message(H2holerMessage.AUTH_SUCCESS, "鉴权成功", h2holerMessage.getSn(), clientContext.getClientId(), 0);
        } else {
            resultMessage = CommonUtil.message(H2holerMessage.AUTH_FAIL, "鉴权失败", h2holerMessage.getSn(), null, 0);
        }
        channel.writeAndFlush(resultMessage);
    }

    private void initContext(ClientContext clientContext, H2holerClient h2holerClient) {
        clientContext.setH2holerClient(h2holerClient);
        List<H2holerPortMapper> h2holerPortMappers = DaoUtil.getH2holerPortMapperDao().findPortMapperByClientId(clientContext.getClientId());
        for (H2holerPortMapper h2holerPortMapper : h2holerPortMappers
        ) {
            int publicPort = h2holerPortMapper.getPublicPort();
            ServerManager.get().registerClient(publicPort, clientContext);
            ServerManager.get().registerPort(publicPort, h2holerPortMapper);
        }
        ClientManager.get().registerClientContext(clientContext);
    }

    private H2holerResult<H2holerClient> getClientByAccessKey(String accessKey) {
        Map<String, Object> clientMap = DaoUtil.getH2holerClientDao().findClientAndUserByAccessKey(accessKey);
        if (clientMap == null) {
            return CommonUtil.fail("KEY不存在");
        }
        if (clientMap.get("user_name") == null) {
            return CommonUtil.fail("没有此用户");
        }
        if (!(Boolean) clientMap.get("user_enabled")) {
            return CommonUtil.fail("用户未启用");
        }
        if (!(Boolean) clientMap.get("client_enabled")) {
            return CommonUtil.fail("客户端未启用");
        }
        H2holerClient h2holerClient = new H2holerClient();
        h2holerClient.setClientId((String) clientMap.get("client_id"));
        h2holerClient.setAccessKey((String) clientMap.get("access_key"));
        h2holerClient.setEnabled((Boolean) clientMap.get("client_enabled"));
        h2holerClient.setId((Integer) clientMap.get("id"));
        h2holerClient.setLastOnlineAt((String) clientMap.get("last_online_at"));
        h2holerClient.setStatus((Integer) clientMap.get("status"));
        return CommonUtil.success(h2holerClient);
    }
}
