package org.hycode.h2holer.server.controllers;


import org.hycode.h2holer.common.constants.H2holerConst;
import org.hycode.h2holer.common.modles.H2holerResult;
import org.hycode.h2holer.common.utils.CommonUtil;
import org.hycode.h2holer.server.annonations.AuthCheck;
import org.hycode.h2holer.server.models.H2holerUser;
import org.hycode.h2holer.server.utils.DaoUtil;
import org.hycode.h2holer.server.utils.ServerUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api/user")
public class UserController {

    @RequestMapping("/add")
    public H2holerResult<?> addUser(@RequestBody H2holerUser h2holerUser) {
        H2holerUser dbH2holerUser = new H2holerUser();
        dbH2holerUser.setUserName(h2holerUser.getUserName());
        dbH2holerUser.setPassword(h2holerUser.getPassword());
        dbH2holerUser.setUid(CommonUtil.randomID());
        DaoUtil.getH2holerUserDao().save(dbH2holerUser);
        return CommonUtil.success();
    }

    @AuthCheck
    @RequestMapping("/delete")
    public H2holerResult<?> deleteUser(@RequestBody H2holerUser h2holerUser) {
        H2holerUser dbUser = DaoUtil.getH2holerUserDao().findByUserName(h2holerUser.getUserName());
        if (dbUser != null) {
            DaoUtil.getH2holerUserDao().delete(dbUser);
        }
        return CommonUtil.success();
    }

    @AuthCheck
    @RequestMapping("/ban")
    public H2holerResult<?> banUser(@RequestBody H2holerUser h2holerUser) {
        H2holerUser dbUser = DaoUtil.getH2holerUserDao().findByUserName(h2holerUser.getUserName());
        if (dbUser == null) {
            return CommonUtil.fail("用户不存在", null);
        } else {
            dbUser.setEnabled(false);
            DaoUtil.getH2holerUserDao().save(dbUser);
            return CommonUtil.success();
        }

    }

    @AuthCheck
    @RequestMapping("/open")
    public H2holerResult<?> openUser(@RequestBody H2holerUser h2holerUser) {
        H2holerUser dbUser = DaoUtil.getH2holerUserDao().findByUserName(h2holerUser.getUserName());
        if (dbUser == null) {
            return CommonUtil.fail("用户不存在", null);
        } else {
            dbUser.setEnabled(true);
            DaoUtil.getH2holerUserDao().save(dbUser);
            return CommonUtil.success();
        }
    }

    @RequestMapping("/login")
    public H2holerResult<?> login(@RequestBody H2holerUser h2holerUser, HttpSession session, HttpServletResponse httpServletResponse) {
        String password = h2holerUser.getPassword();
        H2holerUser dbUser = DaoUtil.getH2holerUserDao().findByUserName(h2holerUser.getUserName());
        if (dbUser == null) {
            return CommonUtil.fail("用户不存在", null);
        }
        if (!dbUser.isEnabled()) {
            return CommonUtil.fail("用户被禁用", null);
        }
        if (!dbUser.getPassword().equals(password)) {
            return CommonUtil.fail("密码错误", null);
        }
        String token = CommonUtil.randomID();
        session.setAttribute(H2holerConst.HEADER_TOKEN_NAME, token);
        ServerUtil.setCookie(H2holerConst.HEADER_TOKEN_NAME, token, H2holerConst.COOKIE_HALF_HOUR, httpServletResponse);
        dbUser.setToken(token);
        DaoUtil.getH2holerUserDao().save(dbUser);
        dbUser.setPassword(null);
        return CommonUtil.success(dbUser);
    }

    @AuthCheck
    @RequestMapping("/logout")
    public H2holerResult<?> logout(@RequestBody H2holerUser h2holerUser) {
        H2holerUser dbUser = DaoUtil.getH2holerUserDao().findByUserName(h2holerUser.getUserName());
        if (dbUser == null) {
            return CommonUtil.fail("用户不存在", null);
        } else {
            dbUser.setToken("0");
            DaoUtil.getH2holerUserDao().save(dbUser);
            return CommonUtil.success();
        }
    }
}
