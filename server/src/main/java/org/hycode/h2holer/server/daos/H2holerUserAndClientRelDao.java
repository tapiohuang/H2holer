package org.hycode.h2holer.server.daos;

import org.hycode.h2holer.server.models.H2holerUserAndClientRel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface H2holerUserAndClientRelDao extends JpaRepository<H2holerUserAndClientRel, Integer> {
}
