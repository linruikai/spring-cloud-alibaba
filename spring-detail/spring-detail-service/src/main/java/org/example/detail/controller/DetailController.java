package org.example.detail.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.base.config.Constant;
import org.example.detail.service.IDetailService;
import org.example.dto.RedisDTO;
import org.example.dto.UserDTO;
import org.example.entity.Detail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DetailController {
    @Autowired
    private IDetailService detailService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @GetMapping("test")
    public String test(@RequestHeader(value = Constant.X_GRAY_VERSION,defaultValue = Constant.X_BASE_VERSION) String version) {
        log.info("this is from detail {}", version);
        return "this is from detail " + version;
    }
    @GetMapping("mysql")
    public UserDTO mysql(@RequestHeader(value = Constant.X_GRAY_VERSION,defaultValue = Constant.X_BASE_VERSION) String version) {
        log.info("this is detail mysql {}", version );
        Detail detail = detailService.getById(1);
        UserDTO userDTO = new UserDTO();
        userDTO.setDetailName(detail.getName());
        return userDTO;
    }

    @GetMapping("redis")
    public RedisDTO redis(@RequestHeader(value = Constant.X_GRAY_VERSION,defaultValue = Constant.X_BASE_VERSION) String version) {
        log.info("this is detail redis {}", version);
        String c = redisTemplate.opsForValue().get("c");
        RedisDTO redisDTO = new RedisDTO();
        redisDTO.setKeyC(c);
        return redisDTO;
    }
}