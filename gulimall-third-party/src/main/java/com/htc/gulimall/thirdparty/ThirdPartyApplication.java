package com.htc.gulimall.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ishuo
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ThirdPartyApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThirdPartyApplication.class, args);
    }
}