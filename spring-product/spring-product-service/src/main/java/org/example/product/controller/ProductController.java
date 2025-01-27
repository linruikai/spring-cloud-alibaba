package org.example.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.base.config.Constant;
import org.example.detail.client.DetailFeignClient;
import org.example.dto.RedisDTO;
import org.example.dto.UserDTO;
import org.example.entity.Detail;
import org.example.entity.Product;
import org.example.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ProductController {

    @Autowired
    private DetailFeignClient detailFeignClient;
    @Autowired
    private IProductService productService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @GetMapping("test")
    public String test(@RequestHeader(value = Constant.X_GRAY_VERSION,defaultValue = Constant.X_BASE_VERSION) String version) {
        log.info("this is from product {}", version);
        detailFeignClient.test();
        return "this is from product " + version;
    }
    @GetMapping("mysql")
    public UserDTO mysql(@RequestHeader(value = Constant.X_GRAY_VERSION,defaultValue = Constant.X_BASE_VERSION) String version) {
        log.info("this is product mysql {}", version);
        Product product = productService.getById(1);
        UserDTO userDTO = detailFeignClient.mysql();
        userDTO.setProductName(product.getName());
        return userDTO;
    }

    @GetMapping("redis")
    public RedisDTO redis(@RequestHeader(value = Constant.X_GRAY_VERSION,defaultValue = Constant.X_BASE_VERSION) String version) {
        log.info("this is product redis {}", version);
        String b = redisTemplate.opsForValue().get("b");
        RedisDTO redisDTO = detailFeignClient.redis();
        redisDTO.setKeyB(b);
        return redisDTO;
    }
}
