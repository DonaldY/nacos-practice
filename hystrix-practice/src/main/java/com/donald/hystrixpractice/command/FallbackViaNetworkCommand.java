package com.donald.hystrixpractice.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 * @author donald
 * @date 2021/09/13
 */
public class FallbackViaNetworkCommand extends HystrixCommand<String> {
    private final int id;

    protected FallbackViaNetworkCommand(int id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceX"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetValueCommand")));
        this.id = id;
    }

    @Override
    protected String run() {
        //        RemoteServiceXClient.getValue(id);
        throw new RuntimeException("force failure for example");
    }

    @Override
    protected String getFallback() {
        // 降级机制执行了另外一个 command
        return new FallbackViaNetwork(id).execute();
    }

    private static class FallbackViaNetwork extends HystrixCommand<String> {
        private final int id;

        public FallbackViaNetwork(int id) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceX"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("FallbackViaNetworkCommand"))
                    // 注意这里：需要使用和正常 command 不一样的线程池
                    // 因为正常 command 执行降级的话有可能是因为线程池满了导致的
                    .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("RemoteServiceXFallback")));
            this.id = id;
        }

        @Override
        protected String run() {
            // 第一级降级策略：通过网络获取数据
            // MemCacheClient.getValue(id);
            return "";
        }

        @Override
        protected String getFallback() {
            // 第二级降级策略：可以使用 stubbed fallback 方案返回残缺的数据
            // 也可以返回一个 null
            return null;
        }
    }
}