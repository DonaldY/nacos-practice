package com.donald.nacosprovider.event;

import java.util.ArrayList;
import java.util.List;

/**
 * @author donald
 * @date 2022/01/27
 */
public class Test {

    @org.junit.Test
    public void test() {

        List<BellListener> bellListeners = new ArrayList<>();
        bellListeners.add(new OpenDoorListener());
        bellListeners.add(new CloseDoorListener());

        BellEvent bellEvent = new BellEvent("铃声", 1);

        for (BellListener bellListener : bellListeners) {

            bellListener.bellEvent(bellEvent);
        }
    }
}
