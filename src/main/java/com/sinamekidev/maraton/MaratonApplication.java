package com.sinamekidev.maraton;

import com.sinamekidev.maraton.cloud.BucketManager;
import com.sinamekidev.maraton.dao.AnswerRepository;
import com.sinamekidev.maraton.dao.CourseRepository;
import com.sinamekidev.maraton.dao.QuestionRepository;
import com.sinamekidev.maraton.dao.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {UserRepository.class, CourseRepository.class, QuestionRepository.class, AnswerRepository.class,})
public class MaratonApplication {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(MaratonApplication.class, args);
	}

}
