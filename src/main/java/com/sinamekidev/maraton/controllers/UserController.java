package com.sinamekidev.maraton.controllers;

import com.sinamekidev.maraton.models.ResponseModel;
import com.sinamekidev.maraton.models.inputs.Login;
import com.sinamekidev.maraton.models.inputs.ProfileUpdate;
import com.sinamekidev.maraton.models.inputs.Register;
import com.sinamekidev.maraton.models.outputs.LoginOutput;
import com.sinamekidev.maraton.models.outputs.CourseOutput;
import com.sinamekidev.maraton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/{user_uid}")
    public ResponseModel getUserFindUid(@PathVariable String user_uid){
        boolean result = userService.findUserByUID(user_uid);
        ResponseModel responseModel = new ResponseModel();
        if (result){
            responseModel.setMessage("Working!");
            responseModel.setStatus(200);
        }
        else{
            responseModel.setStatus(400);
            responseModel.setMessage("Error");
        }
        return responseModel;
    }
    @PostMapping("/login")
    public ResponseModel login(@RequestBody Login loginModel) throws IOException {
        LoginOutput loginOutput = userService.login(loginModel);
        ResponseModel responseModel = new ResponseModel();
        if (loginOutput != null){
            responseModel.setStatus(200);
            responseModel.setMessage(loginOutput);
        }else{
            responseModel.setStatus(400);
            responseModel.setMessage("Error");
        }
        return responseModel;
    }
    @PostMapping("/register")
    public ResponseModel register(@RequestBody Register registerModel){
        boolean result = userService.register(registerModel);
        ResponseModel responseModel = new ResponseModel();
        if (result){
            responseModel.setMessage("OK");
            responseModel.setStatus(200);
        }
        else{
            responseModel.setStatus(400);
            responseModel.setMessage("Error");
        }
        return responseModel;
    }
    @PostMapping("/profile/update")
    public ResponseModel profileUpdate(@RequestBody ProfileUpdate profileUpdate) throws IOException {
        boolean result = userService.profileUpdate(profileUpdate);
        ResponseModel responseModel = new ResponseModel();
        if (result){
            responseModel.setMessage("Working!");
            responseModel.setStatus(200);
        }
        else{
            responseModel.setStatus(400);
            responseModel.setMessage("Error");
        }
        return responseModel;
    }
    @GetMapping("/profile/{user_uid}/courses")
    public ResponseModel profileCourses(@PathVariable String user_uid) throws IOException {
        List<CourseOutput> result = userService.profileCourses(user_uid);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage(result);
        responseModel.setStatus(200);
        return responseModel;
    }
}
