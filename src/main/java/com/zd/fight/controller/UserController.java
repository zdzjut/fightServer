package com.zd.fight.controller;


import com.zd.fight.mapper.UserMapper;
import com.zd.fight.model.User;
import com.zd.fight.service.SequenceService;
import com.zd.fight.util.CommonUtil;
import com.zd.fight.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("fight")
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SequenceService sequenceService;

    //添加用户
    @GetMapping(value = "/addUser")
    public Result addUser(String username, String password) {
        //验证
        User alreadyUser = userMapper.findUserByName(username);
        if (alreadyUser == null) {
            int no = sequenceService.getLatestId("user");
            User user = new User();
            user.setId(no);
            user.setPwd(password);
            user.setName(username);
            user.setBalance(0);
            user.setCreateDate(CommonUtil.getDate());
            userMapper.save(user);
            return new Result(1, "添加成功");
        } else {
            return new Result(0, "添加失败");
        }
    }


    //登录用户
    @RequestMapping(value = "/login")
    public Result login(String username, String password) {
        //验证
        User user = userMapper.findUserByNameAndPwd(username, password);
        if (user == null) {
            return new Result(0, "登录失败");
        } else {
            user.setPwd("******");
            return new Result(1, "登录成功", user);
        }
    }
    //t通过Id
    @RequestMapping(value = "/showUser")
    public Result showUser(Integer id) {
        //验证
        User user = userMapper.findOne(id);
        if (user == null) {
            return new Result(0, "失败");
        } else {
            user.setPwd("******");
            return new Result(1, "成功", user);
        }
    }

    //修改用户姓名密码
    @RequestMapping(value = "/modify")
    public Result modify(Integer id, String username, String password) {
        User user = userMapper.findOne(id);
        if (user != null) {
            user.setId(id);
            user.setName(username);
            user.setPwd(password);
            userMapper.delete(id);
            userMapper.insert(user);
            user.setPwd("******");
            return new Result(1, "修改成功", user);
        } else {
            return new Result(0, "修改失败");
        }
    }

    /**
     * 显示所有用户
     */
    @RequestMapping(value = "/showUserList")
    public Result showUserList() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<User> all = userMapper.findAll(sort);
        return new Result(1, "所有用户", all);

    }


}
