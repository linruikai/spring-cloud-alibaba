package org.example.detail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"org.example.base.config", "org.example.detail"})
public class DetailApplication {
    public static void main(String[] args) {
        SpringApplication.run(DetailApplication.class, args);
    }
}
