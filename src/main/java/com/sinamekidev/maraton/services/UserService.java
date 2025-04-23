package com.sinamekidev.maraton.services;

import com.sinamekidev.maraton.cloud.BucketManager;
import com.sinamekidev.maraton.dao.CourseRepository;
import com.sinamekidev.maraton.dao.UserRepository;
import com.sinamekidev.maraton.models.db.Course;
import com.sinamekidev.maraton.models.db.User;
import com.sinamekidev.maraton.models.inputs.Login;
import com.sinamekidev.maraton.models.inputs.ProfileUpdate;
import com.sinamekidev.maraton.models.inputs.Register;
import com.sinamekidev.maraton.models.outputs.LoginOutput;
import com.sinamekidev.maraton.models.outputs.CourseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private CourseRepository courseRepository;
    private MongoTemplate mongoTemplate;
    @Autowired
    public UserService(UserRepository userRepository,
                       MongoTemplate mongoTemplate,
                       CourseRepository courseRepository)
    {
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
        this.courseRepository = courseRepository;
    }
    public boolean findUserByUID(String user_uid){
        User user = userRepository.findById(user_uid).get();
        return (user != null);
    }
    public LoginOutput login(Login login) throws IOException {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(login.getEmail()).and("password").is(login.getPassword()));
        List<User> user = mongoTemplate.find(query, User.class);
        if (user.isEmpty()){
            return null;
        }
        else{
            User login_user = user.get(0);
            LoginOutput loginOutput = new LoginOutput(login_user);
            return loginOutput;
        }
    }
    @Transactional
    public boolean register(Register register){
        Query checkEmail = new Query();
        checkEmail.addCriteria(Criteria.where("email").is(register.getEmail()));
        List<User> checkUser = mongoTemplate.find(checkEmail, User.class);
        Query checkUsername = new Query();
        checkUsername.addCriteria(Criteria.where("userName").is(register.getUserName()));
        List<User> checkUser2 = mongoTemplate.find(checkUsername, User.class);
        if (checkUser.isEmpty() && checkUser2.isEmpty()){
            User user1 = new User(register);
            user1.setUser_image_url("");
            userRepository.insert(user1);
            return true;
        }
        return false;
    }
    @Transactional
    public boolean profileUpdate(ProfileUpdate profileUpdate) throws IOException {
        boolean result;
        User user = userRepository.findById(profileUpdate.getUser_uid()).get();
        user.setBio(profileUpdate.getBio());
        user.setUserName(profileUpdate.getUserName());
        user.setUser_image_url(BucketManager.getInstance().uploadFile(profileUpdate.getUser_image(), profileUpdate.getUser_uid()));
        userRepository.save(user);
        result = true;
        return result;
    }
    public List<CourseOutput> profileCourses(String user_uid) throws IOException {
        ArrayList courseOutputList = new ArrayList();
        User user = userRepository.findById(user_uid).get();
        List<String> course_list = user.getOwned_course_list();
        for (int i = 0; i<course_list.size();i++) {
            String id = course_list.get(i);
            Course course = courseRepository.findById(id).get();
            courseOutputList.add(new CourseOutput(course));
        }
        return courseOutputList;
    }
}
