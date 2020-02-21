package org.hycode.h2holer.client.utils;

public class ClientUtil {
    public static void exit() {
        try {
            Thread.sleep(1000);
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
