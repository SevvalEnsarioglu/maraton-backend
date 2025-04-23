package com.sinamekidev.maraton.services;

import com.sinamekidev.maraton.cloud.BucketManager;
import com.sinamekidev.maraton.cloud.NotificationManager;
import com.sinamekidev.maraton.dao.AnswerRepository;
import com.sinamekidev.maraton.dao.QuestionRepository;
import com.sinamekidev.maraton.dao.UserRepository;
import com.sinamekidev.maraton.models.db.Answer;
import com.sinamekidev.maraton.models.db.Question;
import com.sinamekidev.maraton.models.db.User;
import com.sinamekidev.maraton.models.inputs.AnswerQuestionInput;
import com.sinamekidev.maraton.models.inputs.ApproveInput;
import com.sinamekidev.maraton.models.inputs.QuestionInput;
import com.sinamekidev.maraton.models.inputs.QuestionVoteInput;
import com.sinamekidev.maraton.models.outputs.QuestionDetailAnswerOutput;
import com.sinamekidev.maraton.models.outputs.QuestionDetailOutput;
import com.sinamekidev.maraton.models.outputs.QuestionOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;
    private UserRepository userRepository;
    private AnswerRepository answerRepository;
    @Autowired
    public QuestionService(QuestionRepository questionRepository,UserRepository userRepository,AnswerRepository answerRepository)
    {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
    }
    public List<QuestionOutput> getQuestions() throws IOException {
        List<Question> questionList = questionRepository.findAll();
        ArrayList<QuestionOutput> questionOutputs = new ArrayList<>();
        for (int i = 0; i<questionList.size(); i++){
            Question question = questionList.get(i);
            QuestionOutput questionOutput = new QuestionOutput(question);
        }
        return questionOutputs;
    }
    @Transactional
    public boolean questionVoteUp(QuestionVoteInput questionVoteInput){
        Question question = questionRepository.findById(questionVoteInput.getQuestion_uid()).get();
        User user = userRepository.findById(questionVoteInput.getUser_uid()).get();
        if (question == null || user == null){
            return false;
        }
        int i = question.getQuestion_up_list().indexOf(user.getUser_uid());
        List a = question.getQuestion_up_list();
        if (i != -1){
            a.add(user.getUser_uid());
        }
        else{
            a.remove(user.getUser_uid());
        }
        question.setQuestion_up_list(a);
        questionRepository.save(question);
        return true;
    }
    @Transactional
    public boolean questionVoteDown(QuestionVoteInput questionVoteInput){
        Question question = questionRepository.findById(questionVoteInput.getQuestion_uid()).get();
        User user = userRepository.findById(questionVoteInput.getUser_uid()).get();
        if (question == null || user == null){
            return false;
        }
        int i = question.getQuestion_down_list().indexOf(user.getUser_uid());
        List a = question.getQuestion_down_list();
        if (i != -1){
            a.add(user.getUser_uid());
        }
        else{
            a.remove(user.getUser_uid());
        }
        question.setQuestion_down_list(a);
        questionRepository.save(question);
        return true;
    }

    @Transactional
    public boolean askQuestion(QuestionInput questionInput) throws IOException {
        Question question = new Question();
        question.setQuestion_text(question.getQuestion_text());
        question.setUser_uid(question.getUser_uid());
        question.setQuestion_uid(UUID.randomUUID().toString());
        String url = BucketManager.getInstance().uploadFile(questionInput.getQuestion_image(),question.getQuestion_uid());
        question.setQuestion_image_url(url);
        questionRepository.save(question);
        return true;
    }

    @Transactional
    public boolean approveAnswer(ApproveInput approveInput) throws IOException {
        User user = userRepository.findById(approveInput.getSolved_user_uid()).get();
        Question question = questionRepository.findById(approveInput.getQuestion_uid()).get();
        Answer answer = answerRepository.findById(approveInput.getAnswer_uid()).get();
        if ((user == null || question == null) || answer == null){
            return false;
        }
        question.setApproved_answer(approveInput.getAnswer_uid());
        user.setScore(user.getScore() + 100);
        User owner_user = userRepository.findById(question.getUser_uid()).get();
        NotificationManager.getInstance().sendNotification(user.getEmail());
        answer.set_approved(true);
        answerRepository.save(answer);
        userRepository.save(user);
        questionRepository.save(question);
        return true;
    }

    @Transactional
    public boolean answerQuestion(AnswerQuestionInput answerQuestionInput) {
        Question question = questionRepository.findById(answerQuestionInput.getQuestion_uid()).get();
        if (question == null){
            return false;
        }
        Answer answer = new Answer();
        answer.setAnswer_uid(UUID.randomUUID().toString());
        answer.setAnswer_text(answerQuestionInput.getAnswer_text());
        answer.setQuestion_uid(question.getQuestion_uid());
        answer.setUser_uid(answerQuestionInput.getUser_uid());
        answer.set_approved(false);
        List a = question.getQuestion_answer_list();
        a.add(answer.getAnswer_uid());
        question.setQuestion_answer_list(a);
        questionRepository.save(question);
        answerRepository.save(answer);
        return true;
    }
    public QuestionDetailOutput questionDetailOutput(String question_uid) throws IOException {
        Question question = questionRepository.findById(question_uid).get();
        ArrayList<QuestionDetailAnswerOutput> answerList = new ArrayList<>();
        for(int i=0;i<question.getQuestion_answer_list().size();i++){
            String answer_uid = question.getQuestion_answer_list().get(i);
            Answer answer = answerRepository.findById(answer_uid).get();
            answerList.add(new QuestionDetailAnswerOutput(answer));
        }
        User user = userRepository.findById(question.getUser_uid()).get();
        String username = user.getUserName();
        QuestionDetailOutput questionDetailOutput = new QuestionDetailOutput(question,username);
        questionDetailOutput.setQuestion_answer_list(answerList);
        return questionDetailOutput;
    }
}
