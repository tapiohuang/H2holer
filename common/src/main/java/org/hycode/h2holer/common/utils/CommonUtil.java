package org.hycode.h2holer.common.utils;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

public class AppUtil {
    public static String randomID() {
        String randomID = UUID.randomUUID().toString();
        randomID = StringUtils.replace(randomID, "-", "");
        return randomID.toLowerCase();
    }

    public static String md5(String s) {
        return DigestUtils.md5DigestAsHex(s.getBytes());
    }
}
