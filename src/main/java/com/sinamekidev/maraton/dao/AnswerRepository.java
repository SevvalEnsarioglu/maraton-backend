package com.sinamekidev.maraton.dao;

import com.sinamekidev.maraton.models.db.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnswerRepository extends MongoRepository<Answer,String> {
}
