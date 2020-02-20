package org.hycode.h2holer.server.utils;

import org.hycode.h2holer.server.daos.*;
import org.springframework.stereotype.Component;

@Component
public class DaoUtil {
    private static DaoUtil daoUtil;
    private final H2holerClientAndPortMapperRelDao h2holerClientAndPortMapperRelDao;
    private final H2holerClientDao h2holerClientDao;
    private final H2holerPortMapperDao h2holerPortMapperDao;
    private final H2holerUserAndClientRelDao h2holerUserAndClientRelDao;
    private final H2holerUserAndPortMapperRelDao h2holerUserAndPortMapperRelDao;
    private final H2holerUserDao h2holerUserDao;

    public DaoUtil(H2holerClientAndPortMapperRelDao h2holerClientAndPortMapperRelDao, H2holerClientDao h2holerClientDao, H2holerPortMapperDao h2holerPortMapperDao, H2holerUserAndClientRelDao h2holerUserAndClientRelDao, H2holerUserAndPortMapperRelDao h2holerUserAndPortMapperRelDao, H2holerUserDao h2holerUserDao) {
        this.h2holerClientAndPortMapperRelDao = h2holerClientAndPortMapperRelDao;
        this.h2holerClientDao = h2holerClientDao;
        this.h2holerPortMapperDao = h2holerPortMapperDao;
        this.h2holerUserAndClientRelDao = h2holerUserAndClientRelDao;
        this.h2holerUserAndPortMapperRelDao = h2holerUserAndPortMapperRelDao;
        this.h2holerUserDao = h2holerUserDao;
        DaoUtil.daoUtil = this;
    }

    public static H2holerClientAndPortMapperRelDao getH2holerClientAndPortMapperRelDao() {
        return daoUtil.h2holerClientAndPortMapperRelDao;
    }

    public static H2holerClientDao getH2holerClientDao() {
        return daoUtil.h2holerClientDao;
    }

    public static H2holerUserAndPortMapperRelDao getH2holerUserAndPortMapperRelDao() {
        return daoUtil.h2holerUserAndPortMapperRelDao;
    }

    public static H2holerUserDao getH2holerUserDao() {
        return daoUtil.h2holerUserDao;
    }

    public static H2holerPortMapperDao getH2holerPortMapperDao() {
        return daoUtil.h2holerPortMapperDao;
    }

    public static H2holerUserAndClientRelDao getH2holerUserAndClientRelDao() {
        return daoUtil.h2holerUserAndClientRelDao;
    }
}
