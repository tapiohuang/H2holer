package org.hycode.h2holer.common.modles;


public class H2holerMessage {
    public static final int CONTROL = 1;
    public static final int DATA = 2;
    public static final int AUTH = 3;
    public static final int AUTH_FAIL = 4;
    public static final int AUTH_SUCCESS = 5;
    public static final int UN_CONNECTED = 6;
    public static final int DATA_START = 7;
    public static final int DATA_END = 8;
    public static final int CLIENT_UNCONNECTED = 9;
    public static final int CLIENT_PUBLIC_INIT = 10;
    public static final int CLIENT_PUBLIC_INIT_SUCCESS = 11;
    public static final int CLIENT_PUBLIC_OFF = 12;
    public static final int INTRA_OFF = 13;

    private int type;
    private String sn;
    private String clientId;
    private byte[] data;
    private int no = 0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "H2holerMessage{" +
                "type=" + type +
                ", sn='" + sn + '\'' +
                ", clientId='" + clientId + '\'' +
                ", data=" + new String(data) +
                ", no=" + no +
                '}';
    }
}
