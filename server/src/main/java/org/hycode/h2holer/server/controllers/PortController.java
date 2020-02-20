package org.hycode.h2holer.server.controllers;

import org.hycode.h2holer.common.modles.H2holerResult;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.hycode.h2holer.server.annonations.AuthCheck;
import org.hycode.h2holer.server.models.H2holerPortMapper;
import org.hycode.h2holer.server.models.H2holerUser;
import org.hycode.h2holer.server.utils.DaoUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/port")
public class PortController {

    @AuthCheck
    @RequestMapping("/all")
    public H2holerResult getAllPort(@RequestBody H2holerUser h2holerUser) {
        return CommonUtil.success("成功", null);
    }

    @AuthCheck
    @RequestMapping("/add")
    public H2holerResult<H2holerPortMapper> addPort(@RequestBody H2holerPortMapper h2holerPortMapper) {
        h2holerPortMapper.setAccessKey(CommonUtil.randomID());
        DaoUtil.getH2holerPortMapperDao().save(h2holerPortMapper);
        return CommonUtil.success("成功", null);
    }

    @AuthCheck
    @RequestMapping("/delete")
    public H2holerResult deletePort() {
        return CommonUtil.success("成功", null);
    }

    @AuthCheck
    @RequestMapping("/ban")
    public H2holerResult banPort() {
        return CommonUtil.success("成功", null);
    }

    @AuthCheck
    @RequestMapping("/open")
    public H2holerResult openPort() {
        return CommonUtil.success("成功", null);
    }
}
