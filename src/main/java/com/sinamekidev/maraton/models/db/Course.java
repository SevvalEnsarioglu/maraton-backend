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
public class Course {
    @Id
    private String course_uid;
    private String course_text;
    private String course_image_url;
    private String teacherName;
    private int score;
    private float rate;
    private List<String> lecture_list;
    public Course(){
        this.lecture_list = new ArrayList<>();
    }
}
