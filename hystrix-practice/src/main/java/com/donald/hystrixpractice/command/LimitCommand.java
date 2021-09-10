package com.donald.hystrixpractice.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

import java.util.concurrent.TimeUnit;

/**
 * @author donald
 * @date 2021/09/10
 */
public class LimitCommand extends HystrixCommand<String> {

    public LimitCommand() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("limit-demo"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        // 配置线程池大小，同时并发能力个数
                        .withCoreSize(2)
                        // 配置等待线程个数；如果不配置该项，则没有等待，超过则拒绝
                        .withMaxQueueSize(5)
                        // 由于 maxQueueSize 是初始化固定的，该配置项是动态调整最大等待数量的
                        // 可以热更新；规则：只能比 MaxQueueSize 小，
                        .withQueueSizeRejectionThreshold(2)
                )
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(2000)) // 修改为 2 秒超时
        );
    }

    @Override
    protected String run() throws Exception {
        TimeUnit.MILLISECONDS.sleep(800);
        return "success";
    }

    @Override
    protected String getFallback() {
        return "降级";
    }
}
