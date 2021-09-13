package com.donald.hystrixpractice.command;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.*;
import org.apache.commons.configuration.AbstractConfiguration;

/**
 * @author donald
 * @date 2021/09/13
 */
public class FacadeCommand extends HystrixCommand<String> {
    private boolean usePrimary;
    private final int id;

    public FacadeCommand(int id) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("SystemX"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("PrimarySecondaryCommand"))
                .andCommandPropertiesDefaults(
                        // 这里使用信号量，因为至少包装其他两个 command，
                        // 其他两个 command 会使用线程池
                        HystrixCommandProperties.Setter()
                                .withExecutionIsolationStrategy(
                                        HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)));
        this.id = id;
        AbstractConfiguration configInstance = ConfigurationManager.getConfigInstance();
        usePrimary = configInstance.getBoolean("primarySecondary.usePrimary");
    }

    @Override
    protected String run() {
        System.out.println("============================= usePrimary：" + usePrimary);
        if (usePrimary) {
            return new PrimaryCommand(id).execute();
        } else {
            return new SecondaryCommand(id).execute();
        }
    }

    @Override
    protected String getFallback() {
        return "static-fallback-" + id;
    }

    // 主
    private static class PrimaryCommand extends HystrixCommand<String> {

        private final int id;

        private PrimaryCommand(int id) {
            super(Setter
                    .withGroupKey(HystrixCommandGroupKey.Factory.asKey("SystemX"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("PrimaryCommand"))
                    .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("PrimaryCommand"))
                    .andCommandPropertiesDefaults(
                            // 设置为超时未 600 毫秒
                            HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(600)));
            this.id = id;
        }

        @Override
        protected String run() {
            // 执行主服务调用
            System.out.println("---------------  " + "responseFromPrimary-" + id);
            return "responseFromPrimary-" + id;
        }

    }

    // 备
    private static class SecondaryCommand extends HystrixCommand<String> {

        private final int id;

        private SecondaryCommand(int id) {
            super(Setter
                    .withGroupKey(HystrixCommandGroupKey.Factory.asKey("SystemX"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("SecondaryCommand"))
                    .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("SecondaryCommand"))
                    .andCommandPropertiesDefaults(
                            // 设置超时为 100 毫秒
                            HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(100)));
            this.id = id;
        }

        @Override
        protected String run() {
            // 由于超时的设置，意味着备用服务将会更快的响应数据
            // 主备设置不同的超时时间，表达的意思是，他们调用响应数据的时间一个慢，一个快
            System.out.println("---------------  " + "responseFromSecondary-" + id);
            return "responseFromSecondary-" + id;
        }
    }
}
