package com.donald.nacosprovider.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.alibaba.cloud.nacos.ribbon.NacosServer;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;
import org.springframework.util.StringUtils;

/**
 * @author donald
 * @date 2022/01/23
 */
public class GrayRule extends AbstractLoadBalancerRule {

    private Random random = new Random();

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        try {
            boolean grayInvocation = false;
            String grayTag = RibbonRequestContextHolder.getCurrentContext().get("Gray");
            if(!StringUtils.isEmpty(grayTag) && grayTag.equals("true")) {
                grayInvocation = true;
            }

            List<Server> serverList = this.getLoadBalancer().getReachableServers();
            List<Server> grayServerList = new ArrayList<>();
            List<Server> normalServerList = new ArrayList<>();
            for(Server server : serverList) {
                NacosServer nacosServer = (NacosServer) server;
                if(nacosServer.getMetadata().containsKey("gray")
                        && nacosServer.getMetadata().get("gray").equals("true")) {
                    grayServerList.add(server);
                } else {
                    normalServerList.add(server);
                }
            }

            if(grayInvocation) {
                return grayServerList.get(random.nextInt(grayServerList.size()));
            } else {
                return normalServerList.get(random.nextInt(normalServerList.size()));
            }
        } finally {
            RibbonRequestContextHolder.clearContext();
        }
    }
}
