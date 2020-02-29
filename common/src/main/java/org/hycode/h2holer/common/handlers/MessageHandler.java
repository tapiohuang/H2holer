package org.hycode.h2holer.common.handlers;

import org.hycode.h2holer.common.modles.MsgEntry;

public interface MessageHandler<Msg extends MsgEntry> {
    boolean onMessage(Msg msgEntry);

    void handleMessage(Msg msgEntry);
}
