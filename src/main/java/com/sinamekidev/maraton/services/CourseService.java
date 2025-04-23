package com.sinamekidev.maraton.services;

import com.sinamekidev.maraton.dao.CourseRepository;
import com.sinamekidev.maraton.dao.UserRepository;
import com.sinamekidev.maraton.models.db.Course;
import com.sinamekidev.maraton.models.db.User;
import com.sinamekidev.maraton.models.inputs.CourseBuyInput;
import com.sinamekidev.maraton.models.outputs.CourseDetailOutput;
import com.sinamekidev.maraton.models.outputs.CourseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    private CourseRepository courseRepository;
    private UserRepository userRepository;
    @Autowired
    public CourseService(CourseRepository courseRepository,UserRepository userRepository){
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }
    public List<CourseOutput> courseOutputs() throws IOException {
        List<Course> courseList = courseRepository.findAll();
        ArrayList<CourseOutput> courseOutputs = new ArrayList<>();
        for (int i = 0; i < courseList.size(); i++) {
            courseOutputs.add(new CourseOutput(courseList.get(i)));
        }
        return courseOutputs;
    }
    public CourseDetailOutput getCourseById(String course_uid) throws IOException {
        Course course = courseRepository.findById(course_uid).get();
        if (course == null){
            return  null;
        }
        return new CourseDetailOutput(course);
    }
    @Transactional
    public boolean buyCourse(CourseBuyInput courseBuyInput){
        boolean result;
        User user = userRepository.findById(courseBuyInput.getUser_uid()).get();
        Course course = courseRepository.findById(courseBuyInput.getCourse_uid()).get();
        if (user == null || course == null){
            result = false;
        }
        else{
            if (user.getScore() >= course.getScore()){
                result = true;
                user.setScore(user.getScore() - course.getScore());
                List a = user.getOwned_course_list();
                a.add(courseBuyInput.getCourse_uid());
                user.setOwned_course_list(a);
                userRepository.save(user);
            }
            else{
                result = false;
            }
        }
        return result;
    }
}
