package com.donald.ribbonconsumer.gray;

/**
 * @author donald
 * @date 2021/01/25
 */
public class RibbonRequestContextHolder {

    private static ThreadLocal<RibbonRequestContext> holder =
            ThreadLocal.withInitial(RibbonRequestContext::new);

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
