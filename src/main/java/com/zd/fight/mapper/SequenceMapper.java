package com.zd.fight.mapper;

import com.zd.fight.model.Sequence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SequenceMapper extends MongoRepository<Sequence, Integer> {
    Sequence findByTableNameEquals(String tableName);

}
