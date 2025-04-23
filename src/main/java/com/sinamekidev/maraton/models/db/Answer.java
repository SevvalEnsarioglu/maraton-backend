package com.sinamekidev.maraton.models.db;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Answer {
    private String user_uid;
    @Id
    private String answer_uid;
    private String answer_text;
    private String question_uid;
    private boolean is_approved;
}
