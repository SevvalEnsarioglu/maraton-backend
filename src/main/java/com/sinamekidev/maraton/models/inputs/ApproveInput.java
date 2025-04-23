package com.sinamekidev.maraton.models.inputs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApproveInput {
    private String solved_user_uid;
    private String question_uid;
    private String answer_uid;
}
