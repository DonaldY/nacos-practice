package com.donald.nacosconsumer.circuitbreaker;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author donald
 * @date 2021/01/27
 */
public class Counter {

    // Closed 状态进入 Open 状态的错误个数阀值
    private final int failureCount;

    // failureCount 统计时间窗口
    private final long failureTimeInterval;

    // 当前错误次数
    private final AtomicInteger currentCount;

    // 上一次调用失败的时间戳
    private long lastTime;

    // Half-Open 状态下成功次数
    private final AtomicInteger halfOpenSuccessCount;

    public Counter(int failureCount, long failureTimeInterval) {
        this.failureCount = failureCount;
        this.failureTimeInterval = failureTimeInterval;
        this.currentCount = new AtomicInteger(0);
        this.halfOpenSuccessCount = new AtomicInteger(0);
        this.lastTime = System.currentTimeMillis();
    }

    public synchronized int incrFailureCount() {
        long current = System.currentTimeMillis();
        if (current - lastTime > failureTimeInterval) { // 超过时间窗口，当前失败次数重置为 0
            lastTime = current;
            currentCount.set(0);
        }
        return currentCount.getAndIncrement();
    }

    public int incrSuccessHalfOpenCount() {
        return this.halfOpenSuccessCount.incrementAndGet();
    }

    public boolean failureThresholdReached() {
        return getCurCount() >= failureCount;
    }

    public int getCurCount() {
        return currentCount.get();
    }

    public synchronized void reset() {
        halfOpenSuccessCount.set(0);
        currentCount.set(0);
    }

}
