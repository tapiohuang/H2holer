package org.hycode.h2holer.server.daos;

import org.hycode.h2holer.server.models.H2holerPortMapper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface H2holerPortMapperDao extends JpaRepository<H2holerPortMapper,Integer> {
}
