package com.donald.hystrixpractice.command;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author donald
 * @date 2021/09/13
 */
public class StubbedCommand extends HystrixObservableCommand<Integer> {

    private int lastSeen = 0;

    public StubbedCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
    }

    @Override
    protected Observable<Integer> construct() {
        // 产生了 1,2,3 个数值（模拟请求），在第 4 个请求时抛出了一个异常
        return Observable.just(1, 2, 3)
                .concatWith(Observable.error(new RuntimeException("forced error")))
                .doOnNext(t1 -> lastSeen = t1)
                .subscribeOn(Schedulers.computation());
    }

    @Override
    protected Observable<Integer> resumeWithFallback() {
        // 走到降级机制里面，会判定这个进度，接着进度返回
        if (lastSeen < 4) {
            return Observable.range(lastSeen + 1, 4 - lastSeen);
        } else {
            return Observable.empty();
        }
    }
}

