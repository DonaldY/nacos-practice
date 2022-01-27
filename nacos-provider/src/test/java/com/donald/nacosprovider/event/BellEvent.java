package com.donald.nacosprovider.event;

import java.util.EventObject;

/**
 * @author donald
 * @date 2022/01/27
 */
public class BellEvent extends EventObject {

    private Integer times;

    public BellEvent(Object source) {
        super(source);
    }

    public BellEvent(Object source, Integer times) {
        super(source);
        this.times = times;
    }

    public Integer getTimes() {
        return this.times;
    }
}
