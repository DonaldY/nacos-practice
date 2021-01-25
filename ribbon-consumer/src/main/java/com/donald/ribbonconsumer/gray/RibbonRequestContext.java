package com.donald.ribbonconsumer.gray;

import java.util.HashMap;
import java.util.Map;

/**
 * @author donald
 * @date 2021/01/25
 */
public class RibbonRequestContext {

    private final Map<String, String> attr = new HashMap<>();

    public String put(String key, String value) {
        return attr.put(key, value);
    }

    public String remove(String key) {
        return attr.remove(key);
    }

    public String get(String key) {
        return attr.get(key);
    }

}