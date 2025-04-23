package com.sinamekidev.maraton.dao;

import com.sinamekidev.maraton.models.db.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Question,String> {

}
