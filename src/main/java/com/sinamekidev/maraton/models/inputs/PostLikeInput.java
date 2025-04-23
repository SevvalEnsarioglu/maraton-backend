package com.sinamekidev.maraton.models.inputs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostLikeInput {
    private String user_uid;
    private String social_post_uid;
}
