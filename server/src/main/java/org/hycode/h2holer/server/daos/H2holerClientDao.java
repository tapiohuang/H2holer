package org.hycode.h2holer.server.daos;

import org.hycode.h2holer.server.models.H2holerClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface H2holerClientDao extends JpaRepository<H2holerClient, Integer> {

    @Query(value = "select h2holer_clients.id, " +
            "       h2holer_clients.access_key, " +
            "       h2holer_clients.client_id, " +
            "       h2holer_clients.enabled as client_enabled, " +
            "       h2holer_clients.last_online_at, " +
            "       h2holer_clients.status, " +
            "       h2hu.user_name, " +
            "       h2hu.enabled            as user_enabled " +
            "from h2holer_clients " +
            "         left join h2holer_user_and_client_rels h2huacr on h2holer_clients.client_id = h2huacr.client_id " +
            "         left join h2holer_users h2hu on h2huacr.uid = h2hu.uid " +
            "where access_key = '8768d9f16441483284913e0b2849c602'",
            nativeQuery = true)
    Map<String, Object> findClientAndUserByAccessKey(String accessKey);
}
