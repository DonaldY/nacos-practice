package com.donald.nacosprovider.event;

import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author donald
 * @date 2021/01/24
 */
@Component
class EventReceiver implements ApplicationListener<EnvironmentChangeEvent> {

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {

        System.out.println(event.getKeys());
    }
}
