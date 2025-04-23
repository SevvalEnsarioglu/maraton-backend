package com.sinamekidev.maraton.controllers;

import com.sinamekidev.maraton.models.ResponseModel;
import com.sinamekidev.maraton.models.db.SocialPost;
import com.sinamekidev.maraton.models.inputs.*;
import com.sinamekidev.maraton.models.outputs.PostDetailOutput;
import com.sinamekidev.maraton.models.outputs.PostOutput;
import com.sinamekidev.maraton.models.outputs.QuestionDetailOutput;
import com.sinamekidev.maraton.models.outputs.QuestionOutput;
import com.sinamekidev.maraton.services.PostService;
import com.sinamekidev.maraton.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/social")
public class SocialController {
    private QuestionService questionService;
    private PostService postService;
    @Autowired
    public SocialController(QuestionService questionService,PostService postService){
        this.questionService = questionService;
        this.postService = postService;
    }
    @GetMapping("/questions")
    public ResponseModel getQuestions() throws IOException {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setStatus(200);
        List<QuestionOutput> questionList = questionService.getQuestions();
        responseModel.setMessage(questionList);
        return responseModel;
    }
    @PostMapping("/questions/up")
    public ResponseModel questionUp(@RequestBody QuestionVoteInput questionVoteInput){
        ResponseModel responseModel = new ResponseModel();
        boolean result = questionService.questionVoteUp(questionVoteInput);
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
    @PostMapping("/question/down")
    public ResponseModel questionDown(@RequestBody QuestionVoteInput questionVoteInput){
        ResponseModel responseModel = new ResponseModel();
        boolean result = questionService.questionVoteDown(questionVoteInput);
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
    @PostMapping("/question/ask")
    public ResponseModel askQuestion(@RequestBody QuestionInput questionInput) throws IOException {
        ResponseModel responseModel = new ResponseModel();
        boolean result = questionService.askQuestion(questionInput);
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
    @PostMapping("/questions/approve")
    public ResponseModel approveAnswer(@RequestBody ApproveInput approveInput) throws IOException {
        ResponseModel responseModel = new ResponseModel();
        boolean result = questionService.approveAnswer(approveInput);
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
    @PostMapping("/questions/answer")
    public ResponseModel answerQuestion(@RequestBody AnswerQuestionInput answerQuestionInput){
        ResponseModel responseModel = new ResponseModel();
        boolean result = questionService.answerQuestion(answerQuestionInput);
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
    @GetMapping("/questions/{question_uid}")
    public ResponseModel getQuestionDetail(@PathVariable String question_uid) throws IOException {
        ResponseModel responseModel = new ResponseModel();
        QuestionDetailOutput questionDetailOutput = questionService.questionDetailOutput(question_uid);
        responseModel.setStatus(200);
        responseModel.setMessage(questionDetailOutput);
        return responseModel;
    }

    @GetMapping("/posts")
    public ResponseModel getOutputs() throws IOException {
        ResponseModel responseModel = new ResponseModel();
        List<PostOutput> postOutputs = postService.getPosts();
        responseModel.setStatus(200);
        responseModel.setMessage(postOutputs);
        return responseModel;
    }
    @GetMapping("/posts/{post_uid}")
    public ResponseModel getPostDetail(@PathVariable String post_uid) throws IOException {
        ResponseModel responseModel = new ResponseModel();
        PostDetailOutput postDetailOutput = postService.getPost(post_uid);
        responseModel.setStatus(200);
        responseModel.setMessage(postDetailOutput);
        return responseModel;
    }
    @PostMapping("/post/create")
    public ResponseModel createPost(@RequestBody CreatePostInput postInput) throws IOException {
        ResponseModel responseModel = new ResponseModel();
        boolean result = postService.createPost(postInput);
        if (result){
            responseModel.setStatus(200);
            responseModel.setMessage("OK");
        }
        else {
            responseModel.setStatus(400);
            responseModel.setMessage("Error");
        }
        return responseModel;
    }
    @PostMapping("/post/comment")
    public ResponseModel createComment(@RequestBody CreateCommentInput createCommentInput){
        ResponseModel responseModel = new ResponseModel();
        boolean result = postService.createComment(createCommentInput);
        if (result){
            responseModel.setStatus(200);
            responseModel.setMessage("OK");
        }
        else {
            responseModel.setStatus(400);
            responseModel.setMessage("Error");
        }
        return responseModel;
    }
    @PostMapping("/post/like")
    public ResponseModel likePost(@RequestBody PostLikeInput postLikeInput){
        ResponseModel responseModel = new ResponseModel();
        boolean result = postService.likePost(postLikeInput);
        if (result){
            responseModel.setStatus(200);
            responseModel.setMessage("OK");
        }
        else {
            responseModel.setStatus(400);
            responseModel.setMessage("Error");
        }
        return responseModel;
    }
}
