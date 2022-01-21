package com.donald.nacosprovider;

import com.donald.nacosprovider.config.GrayRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

@RibbonClients(defaultConfiguration = {GrayRule.class})
@SpringBootApplication
public class NacosProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacosProviderApplication.class, args);
	}
}
