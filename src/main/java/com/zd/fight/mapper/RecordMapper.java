package com.zd.fight.mapper;

import com.zd.fight.model.Record;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecordMapper extends MongoRepository<Record, Integer> {
}
