package com.wyj.apps.sc.servers.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created
 * Author: wyj
 * Date: 2019/8/14
 */
@EnableEurekaServer
@SpringBootApplication
public class ScServerDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScServerDiscoveryApplication.class, args);
    }
}
