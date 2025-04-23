package com.sinamekidev.maraton.dao;

import com.sinamekidev.maraton.models.db.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
}
