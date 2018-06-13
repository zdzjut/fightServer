package com.zd.fight.service;

import com.zd.fight.mapper.SequenceMapper;
import com.zd.fight.model.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SequenceService {
    @Autowired
    private SequenceMapper sequenceMapper;

    /**
     * 获取id
     * @param tableName 集合名
     * @return
     */
    public int getLatestId(String tableName){
        Sequence sequence = sequenceMapper.findByTableNameEquals(tableName);
        if (sequence==null){
            String id = UUID.randomUUID().toString().replace("-", "");
            sequence=new Sequence();
            sequence.setId(id);
            sequence.setNo(1);
            sequence.setTableName(tableName);
            sequenceMapper.save(sequence);
            return 1;
        }
        int no = sequence.getNo()+1;
        sequence.setNo(no);
        sequenceMapper.save(sequence);
        return no;
    }
}
