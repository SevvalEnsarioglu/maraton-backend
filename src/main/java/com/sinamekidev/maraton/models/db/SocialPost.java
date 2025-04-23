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
public class SocialPost {
    @Id
    private String post_uid;
    private String post_image_url;
    private String post_text;
    private List<String> post_like_list;
    private List<String> comment_list;
    private String user_uid;
    public SocialPost(){
        this.post_like_list = new ArrayList<>();
        this.comment_list = new ArrayList<>();
    }
}
