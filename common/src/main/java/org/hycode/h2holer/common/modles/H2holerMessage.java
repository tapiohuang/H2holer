package org.hycode.h2holer.common.modles;

import org.springframework.util.DigestUtils;

public class H2holerMessage {
    public static final int CONTROL = 1;
    public static final int DATA = 2;
    public static final int AUTH = 3;
    public static final int AUTH_FAIL = 4;
    public static final int AUTH_SUCCESS = 5;
    public static final int UN_CONNECTED = 6;
    public static final int DATA_START = 7;
    public static final int DATA_END = 8;

    private String sn;
    private String md5;
    private int type;
    private String data;

    @Override
    public String toString() {
        return "AdangMsg{" +
                "sn='" + sn + '\'' +
                ", md5='" + md5 + '\'' +
                ", type=" + type +
                ", data='" + data + '\'' +
                ", no=" + no +
                '}';
    }

    private int no;

    public H2holerMessage() {
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setSn(byte[] snBytes) {
        this.sn = new String(snBytes);
    }

    public byte[] getSnBytes() {
        return sn.getBytes();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        this.md5 = DigestUtils.md5DigestAsHex(data.getBytes());
    }

    public void setData(byte[] dataBytes) {
        this.data = new String(dataBytes);
        this.md5 = DigestUtils.md5DigestAsHex(dataBytes);
    }

    public byte[] getDataBytes() {
        return data.getBytes();
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public void setMd5(byte[] md5Bytes) {
        this.md5 = new String(md5Bytes);
    }

    public byte[] getMd5Bytes() {
        return this.md5.getBytes();
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
}
