package org.hycode.h2holer.server.daos;

import org.hycode.h2holer.server.models.H2holerClientAndPortMapperRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface H2holerClientAndPortMapperRelDao extends JpaRepository<H2holerClientAndPortMapperRel, Integer> {


}
