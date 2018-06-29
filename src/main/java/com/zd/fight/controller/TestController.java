package com.zd.fight.controller;


import org.springframework.web.bind.annotation.*;

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

    /**
     * 提现列表
     */
    @ResponseBody
    @RequestMapping(value="/example",  produces = "application/json;charset=UTF-8")
    public Object example(String list,String clientId){
        System.out.println(list);
        System.out.println(clientId);

        return list;
    }
    @ResponseBody
    @RequestMapping(value="/example1",  produces = "application/json;charset=UTF-8")
    public Object example1(@RequestBody TestBean testBean){
        System.out.println(testBean);

        return "添加成功";
    }
}
