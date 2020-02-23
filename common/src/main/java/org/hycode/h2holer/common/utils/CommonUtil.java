package org.hycode.h2holer.common.utils;

import com.google.gson.Gson;
import org.hycode.h2holer.common.modles.H2holerMessage;
import org.hycode.h2holer.common.modles.H2holerResult;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

public class CommonUtil {
    public static String randomID() {
        String randomID = UUID.randomUUID().toString();
        randomID = StringUtils.replace(randomID, "-", "");
        return randomID.toLowerCase();
    }

    public static String md5(String s) {
        return DigestUtils.md5DigestAsHex(s.getBytes());
    }

    public static <T> H2holerResult<T> result(int code, String msg, T data) {
        return new H2holerResult<T>(code, msg, data);
    }

    public static H2holerResult<?> success() {
        return result(H2holerResult.OK, "成功", null);
    }

    public static <T> H2holerResult<T> fail(String msg, T data) {
        return result(H2holerResult.FAIL, msg, data);
    }

    public static <T> H2holerResult<T> success(T data) {
        return result(H2holerResult.OK, "成功", data);
    }

    public static <T> H2holerResult<T> fail(String msg) {
        return result(H2holerResult.FAIL, msg, null);
    }


    public static H2holerMessage message(int type, byte[] data, String sn, String clientId, int no) {
        H2holerMessage h2holerMessage = new H2holerMessage();
        h2holerMessage.setData(data);
        h2holerMessage.setType(type);
        h2holerMessage.setSn(sn);
        h2holerMessage.setNo(no);
        h2holerMessage.setClientId(clientId);
        return h2holerMessage;
    }

    public static H2holerMessage message(int type, String data, String sn, String clientId, int no) {
        return message(type, data.getBytes(), sn, clientId, no);
    }

    public static String toJson(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    public static <T> T fromJson(String json, Class<T> tClass) {
        Gson gson = new Gson();
        return gson.fromJson(json, tClass);
    }
}
