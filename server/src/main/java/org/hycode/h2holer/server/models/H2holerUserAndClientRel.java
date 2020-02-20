package org.hycode.h2holer.server.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "h2holer_user_and_client_rels"
)
public class H2holerUserAndClientRel implements Serializable {
    private static final long serialVersionUID = -7794714321367837821L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private int id;
    private String uid;
    private String clientId;

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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "RelUserAngClient{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
