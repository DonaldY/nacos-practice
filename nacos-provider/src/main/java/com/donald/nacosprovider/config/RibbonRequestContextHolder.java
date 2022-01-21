package com.donald.nacosprovider.config;

/**
 * @author donald
 * @date 2022/01/23
 */
public class RibbonRequestContextHolder {

    private static ThreadLocal<RibbonRequestContext> holder
            = ThreadLocal.withInitial(RibbonRequestContext::new);

    public static RibbonRequestContext getCurrentContext() {
        return holder.get();
    }

    public static void setCurrentContext(RibbonRequestContext context) {
        holder.set(context);
    }

    public static void clearContext() {
        holder.remove();
    }
}
