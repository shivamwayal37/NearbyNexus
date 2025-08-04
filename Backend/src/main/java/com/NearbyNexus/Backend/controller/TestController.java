package com.NearbyNexus.Backend.controller;

import com.NearbyNexus.Backend.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private RedisConfig redisConfig;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/redis")
    public String testRedis() {
        redisConfig.redisTemplate(redisTemplate.getConnectionFactory());
        // Set a test key-value pair in Redis and retrieve it
        redisTemplate.opsForValue().set("testKey", "testValue");
        return (String) redisTemplate.opsForValue().get("testKey");
    }

    @GetMapping("/websocket")
    public String testWebSocket() {
        // This endpoint is just a placeholder to demonstrate WebSocket functionality
        return "WebSocket endpoint is working";
    }
}
