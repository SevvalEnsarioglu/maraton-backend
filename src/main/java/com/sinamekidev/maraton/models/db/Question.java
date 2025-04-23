package com.sinamekidev.maraton.models.db;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@Document
public class Question {
    @Id
    private String question_uid;
    private String question_image_url;
    private String question_text;
    private List<String> question_up_list;
    private List<String> question_down_list;
    private String approved_answer;
    private String user_uid;
    private List<String> question_answer_list;
    public Question(){
        this.question_answer_list = new ArrayList<>();
    }
}
