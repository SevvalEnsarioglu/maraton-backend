package com.sinamekidev.maraton.dao;

import com.sinamekidev.maraton.models.db.SocialPost;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<SocialPost,String> {

}
