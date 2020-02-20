package org.hycode.h2holer.server.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "h2holer_client_and_port_mapper_rels"
)
public class H2holerClientAndPortMapperRel implements Serializable {
    private static final long serialVersionUID = 2084369245114021165L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private int id;
    private String clientId;
    private String portMapperId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPortMapperId() {
        return portMapperId;
    }

    public void setPortMapperId(String portMapperId) {
        this.portMapperId = portMapperId;
    }

    @Override
    public String toString() {
        return "RelClientAndPortMapper{" +
                "id=" + id +
                ", clientId='" + clientId + '\'' +
                ", portMapperId='" + portMapperId + '\'' +
                '}';
    }
}
