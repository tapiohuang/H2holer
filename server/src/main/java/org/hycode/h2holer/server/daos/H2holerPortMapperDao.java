package org.hycode.h2holer.server.daos;

import org.hycode.h2holer.server.models.H2holerPortMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface H2holerPortMapperDao extends JpaRepository<H2holerPortMapper,Integer> {

    @Query(value = "select h2holer_port_mappers.id, " +
            "       h2holer_port_mappers.access_key, " +
            "       h2holer_port_mappers.enabled, " +
            "       h2holer_port_mappers.inner_addr, " +
            "       h2holer_port_mappers.inner_port, " +
            "       h2holer_port_mappers.port_mapper_id, " +
            "       h2holer_port_mappers.port_type, " +
            "       h2holer_port_mappers.public_addr, " +
            "       h2holer_port_mappers.public_domain, " +
            "       h2holer_port_mappers.public_port " +
            "from h2holer_port_mappers " +
            "         left join h2holer_client_and_port_mapper_rels acapmr on h2holer_port_mappers.port_mapper_id = acapmr.port_mapper_id " +
            "where client_id = ?1", nativeQuery = true)
    List<H2holerPortMapper> findPortMapperByClientId(String clientId);
}
