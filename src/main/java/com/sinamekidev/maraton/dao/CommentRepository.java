package com.sinamekidev.maraton.dao;

import com.sinamekidev.maraton.models.db.SocialPostComment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<SocialPostComment,String> {
}
