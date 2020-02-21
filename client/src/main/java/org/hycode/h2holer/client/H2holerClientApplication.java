package org.hycode.h2holer.client;

import org.hycode.h2holer.client.managers.ClientManager;
import org.hycode.h2holer.client.managers.IntraManager;


public class H2holerClientApplication {
    public static void main(String[] args) {
        ClientManager clientManager = ClientManager.get();
        clientManager.init();

        IntraManager intraManager = IntraManager.get();
        intraManager.init();
    }
}
