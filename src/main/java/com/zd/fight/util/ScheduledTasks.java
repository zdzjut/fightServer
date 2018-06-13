package com.zd.fight.util;

import com.zd.fight.mapper.LockGameMapper;
import com.zd.fight.mapper.RecordMapper;
import com.zd.fight.model.LockGame;
import com.zd.fight.model.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private LockGameMapper lockGameMapper;


    @Scheduled(fixedDelay = 1800000)        //fixedDelay = 1800000 表示当前方法执行完毕半小时后，Spring scheduling会再次调用该方法
    public void delete() {
        List<Record> all = recordMapper.findAll();
        for (Record record : all) {
            long time = record.getDate().getTime();
            long now = new Date().getTime();
//            现在距离开局已经3小时后,解锁
            if (time + 3 * 60 * 60 * 1000 - now < 0) {
                LockGame lockGame = lockGameMapper.findByRecordId(record.getId());
                lockGameMapper.delete(lockGame.getId());
            }
        }
    }

//    @Scheduled(fixedRate = 5000)        //fixedRate = 5000表示当前方法开始执行5000ms后，Spring scheduling会再次调用该方法
//    public void testFixedRate() {
//    }

//    @Scheduled(initialDelay = 1000, fixedRate = 5000)   //initialDelay = 1000表示延迟1000ms执行第一次任务
//    public void testInitialDelay() {
//    }
//
//    @Scheduled(cron = "0 0/1 * * * ?")  //cron接受cron表达式，根据cron表达式确定定时规则
//    public void testCron() {
//    }

}