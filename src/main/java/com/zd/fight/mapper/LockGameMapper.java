package com.zd.fight.mapper;

import com.zd.fight.model.LockGame;
import com.zd.fight.model.Record;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LockGameMapper extends MongoRepository<LockGame, Integer> {
    LockGame findByRecordIdAndUserId(Integer recordId,Integer userId);
    LockGame findByRecordId(Integer recordId);
}
