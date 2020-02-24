package org.hycode.h2holer.server.workers;

import org.hycode.h2holer.common.utils.CommonUtil;
import org.hycode.h2holer.common.workers.BaseWorker;
import org.hycode.h2holer.server.services.client.ClientService;
import org.hycode.h2holer.server.services.publicly.PublicService;

public class CheckTranslateWorker extends BaseWorker {
    private TranslateToClientWorker translateToClientWorker;
    private TranslateToPublicWorker translateToPublicWorker;
    private int checkTimes = 0;

    public CheckTranslateWorker() {
        translateToPublicWorker = PublicService.getTranslateToPublicWorker();
        translateToClientWorker = ClientService.getTranslateToClientWorker();
    }


    @Override
    public void main() {
        if (!translateToPublicWorker.isProcess()) {
            if (translateToPublicWorker.getSize() > 0) {
                translateToPublicWorker.start();
                checkTimes = 0;
            }
        }
        if (!translateToClientWorker.isProcess()) {
            if (translateToClientWorker.getSize() > 0) {
                translateToClientWorker.start();
                checkTimes = 0;
            }
        }
        checkTimes++;
        CommonUtil.waitMoment(checkTimes * 1000);
    }
}
