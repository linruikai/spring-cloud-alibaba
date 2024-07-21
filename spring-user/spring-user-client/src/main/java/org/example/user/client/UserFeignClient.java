package org.example.user.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("spring-user-service")
public interface UserFeignClient {
}
