package org.hycode.h2holer.server.models;


import javax.persistence.*;

@Entity
@Table(name = "h2holer_port_mappers")
public class H2holerPortMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private int id;
    private String accessKey;
    private String portMapperId;
    private Integer publicPort;
    private String publicAddr;
    private String publicDomain;
    private String portType;
    private String innerAddr;
    private Integer innerPort;
    private Boolean enabled;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }


    public Integer getPublicPort() {
        return publicPort;
    }

    public void setPublicPort(Integer publicPort) {
        this.publicPort = publicPort;
    }

    public String getPublicAddr() {
        return publicAddr;
    }

    public void setPublicAddr(String publicAddr) {
        this.publicAddr = publicAddr;
    }

    public String getPublicDomain() {
        return publicDomain;
    }

    public void setPublicDomain(String publicDomain) {
        this.publicDomain = publicDomain;
    }


    public String getInnerAddr() {
        return innerAddr;
    }

    public void setInnerAddr(String innerAddr) {
        this.innerAddr = innerAddr;
    }

    public Integer getInnerPort() {
        return innerPort;
    }

    public void setInnerPort(Integer innerPort) {
        this.innerPort = innerPort;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPortMapperId() {
        return portMapperId;
    }

    public void setPortMapperId(String portMapperId) {
        this.portMapperId = portMapperId;
    }

    public String getPortType() {
        return portType;
    }

    public void setPortType(String portType) {
        this.portType = portType;
    }

    @Override
    public String toString() {
        return "H2holerPortMapper{" +
                "id=" + id +
                ", accessKey='" + accessKey + '\'' +
                ", portMapperId='" + portMapperId + '\'' +
                ", publicPort=" + publicPort +
                ", publicAddr='" + publicAddr + '\'' +
                ", publicDomain='" + publicDomain + '\'' +
                ", portType='" + portType + '\'' +
                ", innerAddr='" + innerAddr + '\'' +
                ", innerPort=" + innerPort +
                ", enabled=" + enabled +
                '}';
    }
}
