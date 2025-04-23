package com.sinamekidev.maraton.models.outputs;

import com.sinamekidev.maraton.models.db.SocialPostComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDetailCommentOutput {
    private String comment_uid;
    private String user_uid;
    private String comment_text;
    public PostDetailCommentOutput(SocialPostComment postComment){
        this.comment_text = postComment.getComment_text();
        this.user_uid = postComment.getUser_uid();
        this.comment_uid = postComment.getComment_uid();
    }
}
