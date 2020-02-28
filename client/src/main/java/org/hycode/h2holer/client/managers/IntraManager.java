package org.hycode.h2holer.client.managers;

import org.hycode.h2holer.client.contexts.IntraContext;

import java.util.HashMap;

public class IntraManager {
    private static IntraManager intraManager;
    private final HashMap<String, IntraContext> intraContextHashMap;

    public IntraManager() {
        intraContextHashMap = new HashMap<>();
    }

    public static IntraManager get() {
        if (intraManager == null) {
            intraManager = new IntraManager();
        }
        return intraManager;
    }

    public IntraContext getIntraContext(String publicId) {
        return intraContextHashMap.get(publicId);
    }

    public void applyIntraContext(String publicId) {
        synchronized (intraContextHashMap) {
            IntraContext intraContext = new IntraContext();
            intraContext.setPublicId(publicId);
            intraContext.setClientContext(ClientManager.get().getClientContext());
            intraContextHashMap.put(publicId, intraContext);
            //return intraContext;
        }
    }


    public void closeIntraContext(IntraContext intraContext) {
        intraContextHashMap.remove(intraContext.getPublicId());
        intraContext.close();
    }
}
