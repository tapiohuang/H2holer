package org.hycode.h2holer.common.modles;

import org.hycode.h2holer.common.utils.CommonUtil;

public class H2holerPublicConfig {
    private String protocol;
    private String host;
    private int port;
    private String sn;

    public H2holerPublicConfig(String protocol, String host, int port, String sn) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.sn = sn;
    }

    @Override
    public String toString() {
        return "H2holerPublicConfig{" +
                "protocol='" + protocol + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", sn='" + sn + '\'' +
                '}';
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public String toJson() {
        return CommonUtil.toJson(this);
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
