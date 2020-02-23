package org.hycode.h2holer.common.modles;

public class H2holerPort {
    private final int port;
    private final String type;

    public H2holerPort(int port, String type) {
        this.port = port;
        this.type = type;
    }

    public int getPort() {
        return port;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "H2holerPort{" +
                "port=" + port +
                ", type='" + type + '\'' +
                '}';
    }
}
