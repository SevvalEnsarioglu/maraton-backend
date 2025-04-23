package com.sinamekidev.maraton.controllers;

import com.sinamekidev.maraton.models.ResponseModel;
import com.sinamekidev.maraton.models.inputs.CourseBuyInput;
import com.sinamekidev.maraton.models.outputs.CourseDetailOutput;
import com.sinamekidev.maraton.models.outputs.CourseOutput;
import com.sinamekidev.maraton.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private CourseService courseService;
    @Autowired
    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }
    @GetMapping("")
    public ResponseModel courseList() throws IOException {
        ResponseModel responseModel = new ResponseModel();
        List<CourseOutput> courseOutputList = courseService.courseOutputs();
        responseModel.setStatus(200);
        responseModel.setMessage(courseOutputList);
        return responseModel;
    }
    @GetMapping("/{course_uid}")
    public ResponseModel courseDetail(@PathVariable String course_uid) throws IOException {
        ResponseModel responseModel = new ResponseModel();
        CourseDetailOutput courseDetailOutput = courseService.getCourseById(course_uid);
        if (courseDetailOutput == null){
            responseModel.setStatus(400);
            responseModel.setMessage("Error");
            return responseModel;
        }
        responseModel.setStatus(200);
        responseModel.setMessage(courseDetailOutput);
        return responseModel;
    }
    @PostMapping("/courses/buy")
    public ResponseModel buyCourse(@RequestBody CourseBuyInput courseBuyInput){
        ResponseModel responseModel = new ResponseModel();
        boolean result = courseService.buyCourse(courseBuyInput);
        if (result){
            responseModel.setStatus(200);
            responseModel.setMessage("OK");
        }
        else{
            responseModel.setStatus(400);
            responseModel.setMessage("Error");
        }
        return responseModel;
    }
}
