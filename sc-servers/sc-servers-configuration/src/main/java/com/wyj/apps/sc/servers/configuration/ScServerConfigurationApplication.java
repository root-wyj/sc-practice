package com.wyj.apps.sc.servers.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created
 * Author: wyj
 * Date: 2019/8/14
 */

@SpringBootApplication
@EnableDiscoveryClient
public class ScServerConfigurationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScServerConfigurationApplication.class, args);
    }
}
