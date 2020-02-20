package org.hycode.h2holer.server.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "h2holer_user_and_port_mapper_rels"
)
public class H2holerUserAndPortMapperRel implements Serializable {
    private static final long serialVersionUID = 4685027813743138221L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private int id;

    private String uid;
    private String portMapperId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPortMapperId() {
        return portMapperId;
    }

    public void setPortMapperId(String portMapperId) {
        this.portMapperId = portMapperId;
    }

    @Override
    public String toString() {
        return "RelPortMapperAndUser{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", portMapperId='" + portMapperId + '\'' +
                '}';
    }
}
