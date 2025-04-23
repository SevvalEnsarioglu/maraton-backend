package com.sinamekidev.maraton.dao;

import com.sinamekidev.maraton.models.db.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course,String> {
}
