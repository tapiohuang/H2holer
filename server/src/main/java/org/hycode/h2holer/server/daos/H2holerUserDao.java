package org.hycode.h2holer.server.daos;

import org.hycode.h2holer.server.models.H2holerUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface H2holerUserDao extends JpaRepository<H2holerUser, Integer> {

    H2holerUser findByUserName(String userName);
}
