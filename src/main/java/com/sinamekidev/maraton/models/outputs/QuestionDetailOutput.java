package com.sinamekidev.maraton.models.outputs;

import com.sinamekidev.maraton.cloud.BucketManager;
import com.sinamekidev.maraton.models.db.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuestionDetailOutput {
    private String question_uid;
    private byte[] question_image;
    private String question_text;
    private int question_upcount;
    private int question_answer_count;
    private String user_uid;
    private String userName;
    private String approved_answer;
    private List<QuestionDetailAnswerOutput> question_answer_list;

    public QuestionDetailOutput(Question question,String userName) throws IOException {
        this.question_uid = question.getQuestion_uid();
        this.question_image = BucketManager.getInstance().readFile(question.getQuestion_image_url());
        this.question_upcount = question.getQuestion_up_list().size() - question.getQuestion_down_list().size();
        this.question_answer_count = question_answer_list.size();
        this.user_uid = question.getUser_uid();
        this.userName = userName;
        this.approved_answer = question.getApproved_answer();
    }
}
