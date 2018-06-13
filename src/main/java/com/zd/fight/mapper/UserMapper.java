package com.zd.fight.mapper;

import com.zd.fight.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMapper extends MongoRepository<User, Integer> {
    User findUserByName(String name);

    User findUserByNameAndPwd(String name, String pwd);
}
