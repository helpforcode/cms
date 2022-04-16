package com.example.cms.controller;


import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("redis")
public class RedisController {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("value")
    public String value() {
        redisTemplate.opsForValue().set("Hello", "World");
        return redisTemplate.opsForValue().get("Hello");
    }

    @GetMapping("list")
    public List<String> list() {
        ListOperations<String, String> ops = redisTemplate.opsForList();
        redisTemplate.delete("Men");
        ops.rightPush("Men", "Trump");
        ops.rightPush("Men", "Biden");
        return ops.range("Men", 0, -1);
    }
    // todo: list of Object?

    @GetMapping("hash")
    public Map<String, Integer> hash() {
        HashOperations<String, String, Integer> ops = redisTemplate.opsForHash();
        ops.put("ages", "Jim", 23);
        ops.put("ages", "Tom", 24);
        Set<String> names =  ops.keys("ages");
        System.out.println(names); // [Jim, Tom]
        List<Integer> ages =  ops.values("ages");
        System.out.println(ages); // [23, 24]
        Map<String, Integer> map = ops.entries("ages");
        System.out.println(map); // {Jim=23, Tom=24}
        System.out.println("Age of tom:");
        System.out.println(ops.get("ages", "Tom")); // 24
        return map;
    }
    // todo: other operations
}
