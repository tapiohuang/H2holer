package org.hycode.h2holer.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.netty.channel.Channel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(
        name = "h2holer_clients"
)
public class H2holerClient implements Serializable {
    private static final long serialVersionUID = 4470342425409301145L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String clientId;
    private String accessKey;
    private Boolean enabled;
    private Integer status;
    private String lastOnlineAt;
    @Transient
    @JsonIgnore
    private Channel clientChannel;


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof H2holerClient)) {
            return false;
        }
        H2holerClient other = (H2holerClient) o;
        return this.clientId.equals(other.clientId);
    }

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

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLastOnlineAt() {
        return lastOnlineAt;
    }

    public void setLastOnlineAt(String lastOnlineAt) {
        this.lastOnlineAt = lastOnlineAt;
    }

    public Channel getClientChannel() {
        return clientChannel;
    }

    public void setClientChannel(Channel clientChannel) {
        this.clientChannel = clientChannel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId);
    }

    @Override
    public String toString() {
        return "H2holerClient{" +
                "id=" + id +
                ", clientId='" + clientId + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", enabled=" + enabled +
                ", status=" + status +
                ", lastOnlineAt='" + lastOnlineAt + '\'' +
                ", clientChannel=" + clientChannel +
                '}';
    }
}
