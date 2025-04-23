package com.sinamekidev.maraton.models.outputs;

import com.sinamekidev.maraton.models.db.Answer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionDetailAnswerOutput {
    private String user_uid;
    private String answer_text;
    private String answer_uid;
    private boolean is_approved;
    public QuestionDetailAnswerOutput(Answer answer){
        this.user_uid = answer.getUser_uid();
        this.answer_text = answer.getAnswer_text();
        this.answer_uid = answer.getAnswer_uid();
        this.is_approved = answer.is_approved();
    }
}
