package com.donald.nacosprovider.event;

import java.util.EventListener;

/**
 * @author donald
 * @date 2022/01/27
 */
public interface BellListener extends EventListener {

    void bellEvent(BellEvent bellEvent);
}

class OpenDoorListener implements BellListener {

    @Override
    public void bellEvent(BellEvent bellEvent) {

        Integer times = bellEvent.getTimes();

        if (1 == times) {
            System.out.println("响铃 " + times + " 声, " + "开门啊！！！");
        }
    }
}

class CloseDoorListener implements BellListener {

    @Override
    public void bellEvent(BellEvent bellEvent) {

        Integer times = bellEvent.getTimes();

        if (2 == times) {
            System.out.println("响铃 " + times + " 声, " + "关门啊！！！");
        }
    }
}
