package org.example.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.base.config.Constant;
import org.example.dto.RedisDTO;
import org.example.dto.UserDTO;
import org.example.entity.User;
import org.example.product.client.ProductFeignClient;
import org.example.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private IUserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @GetMapping("test")
    public String test(@RequestHeader(value = Constant.X_GRAY_VERSION,defaultValue = Constant.X_BASE_VERSION) String version) {
        log.info("this is from user {}", version);
        productFeignClient.test();
        return "this is from user " + version;
    }

    @GetMapping("mysql")
    public UserDTO mysql(@RequestHeader(value = Constant.X_GRAY_VERSION,defaultValue = Constant.X_BASE_VERSION) String version) {
        log.info("this is from user mysql {}", version);
        User user = userService.getById(1);
        UserDTO userDTO = productFeignClient.mysql();
        userDTO.setId(1);
        userDTO.setUserName(user.getName());
        return userDTO;
    }

    @GetMapping("redis")
    public RedisDTO redis(@RequestHeader(value = Constant.X_GRAY_VERSION,defaultValue = Constant.X_BASE_VERSION) String version) {
        log.info("this is user redis {}", version);
        String a = redisTemplate.opsForValue().get("a");
        RedisDTO redisDTO = productFeignClient.redis();
        redisDTO.setKeyA(a);
        return redisDTO;
    }

    @GetMapping("kafka")
    public void kafka(@RequestParam(value ="message")String message) {
        String topic = "topic-a"; // 基础服务 topic
        kafkaTemplate.send(topic, message);
    }
}
