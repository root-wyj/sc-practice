package com.wyj.apps.sc.commerce.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author wuyingjie <wuyingjie@kuaishou.com>
 * Created on 2021-04-26
 */
@SpringBootApplication
@EnableEurekaClient
public class ECommerceOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceOrderApplication.class, args);
    }
}
