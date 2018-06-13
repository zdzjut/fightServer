package com.zd.fight.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RequestMapping("api")
@RestController
public class TestController {


    @GetMapping("ttt")
    public List<Map<String, String>> getTest() {
        System.out.println(1);
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("id", "" + i);
            map.put("name", "小" + i + "条");
            map.put("address", "怡景苑" + i + "幢");
            list.add(map);
        }
        return list;
    }

    @RequestMapping("test")
    public String test(String test) {
        System.out.println(test);
        return "hello world";
    }
}
