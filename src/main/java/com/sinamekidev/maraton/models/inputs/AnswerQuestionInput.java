package com.sinamekidev.maraton.models.inputs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerQuestionInput {
    private String user_uid;
    private String question_uid;
    private String answer_text;
}
