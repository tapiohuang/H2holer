package org.hycode.h2holer.server.services.client;

import org.hycode.h2holer.common.modles.H2holerResult;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.hycode.h2holer.server.models.H2holerClient;
import org.hycode.h2holer.server.utils.DaoUtil;

import java.util.Map;

public class ClientManager {

    public static H2holerResult<H2holerClient> getClientByAccessKey(String accessKey) {
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
