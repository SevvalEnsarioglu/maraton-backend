package com.sinamekidev.maraton.models.db;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class SocialPostComment {
    @Id
    private String comment_uid;
    private String post_uid;
    private String user_uid;
    private String comment_text;
}
