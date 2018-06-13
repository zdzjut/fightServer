package com.zd.fight.controller;


import com.zd.fight.mapper.LockGameMapper;
import com.zd.fight.mapper.RecordMapper;
import com.zd.fight.mapper.UserMapper;
import com.zd.fight.model.LockGame;
import com.zd.fight.model.Record;
import com.zd.fight.model.User;
import com.zd.fight.service.SequenceService;
import com.zd.fight.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("fight")
@RestController
public class RecordController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private LockGameMapper lockGameMapper;
    @Autowired
    private SequenceService sequenceService;


    //选择用户和倍率
    //{ user-list ,list{record-list} },user 第一个是序号
    @RequestMapping("/choosePerson")
    public Result choose(String users, int times, Integer loginUserId) {
        List<LockGame> all = lockGameMapper.findAll();

        String[] userIds = users.split(",");
        for (LockGame lockGame : all) {
            for (String userId : userIds) {
                if (Integer.parseInt(userId) == lockGame.getUserId()) return new Result(0, "失败，用户已被其他局添加！");
            }
        }
        Record record = new Record();
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setName("序号");
        list.add(user);
        for (int i = 1; i < 5; i++) {
            String s = userIds[i - 1];
            Integer tempId = Integer.parseInt(s);
            User one = userMapper.findOne(tempId);
            one.setBalance(0);//这局初始化
            list.add(one);
        }
        int id = sequenceService.getLatestId("record");
        record.setId(id);
        record.setUsers(list);
        record.setTimes(times);
        record.setDate(new Date());
        recordMapper.save(record);
        //继续时需要验证是否锁住，
        int lockGameId = sequenceService.getLatestId("lockGame");
        lockGameMapper.save(new LockGame(lockGameId, id, loginUserId, true));
        return new Result(1, "添加成功", record);
    }

    /**
     * @param recordId  局数ID
     * @param landowner 地主
     * @param times     倍数
     */
    @RequestMapping("/takeNote")
    public Result create(Integer recordId, int landowner, int times, Integer loginUserId) {
        LockGame lockGame = lockGameMapper.findByRecordIdAndUserId(recordId, loginUserId);
        //如果lockGame不为空，证明这个用户可以操作这个数据
        if (lockGame == null) return new Result(0, "你无法暗地操纵！");
        Record record = recordMapper.findOne(recordId);//找到本局
        List<List<Integer>> rounds = record.getRounds();//本局回合
        List<User> users = record.getUsers();//用户，第一个是序号
        if (rounds == null) rounds = new ArrayList<>();//
        List<Integer> list = new ArrayList<>();
        int basicFee = record.getTimes() * times;//原先选择倍数*这次翻倍数
        list.add(rounds.size() + 1);
        for (int i = 1; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getId() == landowner) {//地主此次结果
                list.add(user.getBalance() + basicFee * 3);
                user.setBalance(user.getBalance() + basicFee * 3);

                User model = userMapper.findOne(user.getId());
                model.setBalance(model.getBalance() + basicFee);
                userMapper.delete(user.getId());
                userMapper.insert(model);
            } else {
                list.add(user.getBalance() - basicFee);
                user.setBalance(user.getBalance() - basicFee);
                User model = userMapper.findOne(user.getId());
                model.setBalance(model.getBalance() - basicFee);
                userMapper.delete(user.getId());
                userMapper.insert(model);
            }

        }
        rounds.add(0, list);
        record.setRounds(rounds);
        record.setUsers(users);
        recordMapper.delete(record.getId());
        recordMapper.insert(record);
        return new Result(1, "添加成功", record);

    }

    @RequestMapping("/exit")
    public Result exit(Integer recordId, Integer loginUserId) {
        LockGame lockGame = lockGameMapper.findByRecordIdAndUserId(recordId, loginUserId);
        lockGameMapper.delete(lockGame);
        return new Result(1, "退出成功");
    }

    /**
     * 显示单局
     */
    @RequestMapping(value = "/showRecord")
    public Result showRecord(Integer recordId) {
        Record record = recordMapper.findOne(recordId);
        List<User> users = record.getUsers();
        for (User user : users) {
            user.setPwd("******");
        }
        return new Result(1, "成功", record);
    }

    /**
     * 显示历史记录列表
     */
    @RequestMapping(value = "/showRecordList")
    public Result showHistoryList() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<Record> all = recordMapper.findAll(sort);
        return new Result(1, "历史记录", all);

    }

    /**
     * 继续游戏
     */
    @RequestMapping(value = "/continueGame")
    public Result continueGame(Integer recordId, Integer loginUserId) {
        Record one = recordMapper.findOne(recordId);
        if (one == null) return new Result(0, "该局不存在！");
        LockGame lockGame = lockGameMapper.findByRecordIdAndUserId(recordId, loginUserId);
        //该用户可以修改
        return lockGame == null ? new Result(0, "本局锁定，无权修改！") : new Result(1);
    }

    /**
     * 删除单回合
     */
    @RequestMapping(value = "/delRound")
    public Result delRound(Integer recordId) {
        Record one = recordMapper.findOne(recordId);
        List<List<Integer>> rounds = one.getRounds();
        if (rounds == null || rounds.size() == 0) return new Result(0, "没有可删除的内容！！");
        List<User> users = one.getUsers();
        int size = users.size();
        List<Integer> round0 = rounds.get(0);//最新
        List<Integer> round1 = rounds.get(1);//如果有 上一条
        for (int i = 0; i < size; i++) {
            int preview = round1.get(i);
            if (size == 1) preview = 0;
            User user = users.get(i);
            User model = userMapper.findOne(user.getId());
            //减去最新的，加上旧的
            model.setBalance(model.getBalance() - round0.get(i) + preview);
            userMapper.delete(user.getId());
            userMapper.insert(model);
            user.setBalance(preview);
        }
        rounds.remove(0);
        one.setRounds(rounds);
        one.setUsers(users);
        recordMapper.delete(one.getId());
        recordMapper.insert(one);
        return new Result(1, "成功");
    }


    /**
     * 删除lockGame
     */
    @RequestMapping(value = "/dropLock")
    public Result dropLock() {
        List<LockGame> all = lockGameMapper.findAll();
        if (all.size()==0)        return new Result(0, "没了");

        lockGameMapper.delete(all.get(all.size()-1));
        return new Result(1, "所有用户", all);

    }
}
