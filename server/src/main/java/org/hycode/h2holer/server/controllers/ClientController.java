package org.hycode.h2holer.server.controllers;

import org.hycode.h2holer.common.modles.H2holerResult;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.hycode.h2holer.server.models.H2holerClient;
import org.hycode.h2holer.server.utils.DaoUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/client")
public class ClientController {

    @RequestMapping("/add")
    public H2holerResult<H2holerClient> addClient(@RequestBody H2holerClient aDangClient) {
        aDangClient.setAccessKey(CommonUtil.randomID());
        aDangClient.setClientId(CommonUtil.randomID());
        DaoUtil.getH2holerClientDao().save(aDangClient);
        return CommonUtil.success(null);
    }
}
