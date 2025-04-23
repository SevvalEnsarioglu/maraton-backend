package com.sinamekidev.maraton.models.outputs;

import com.sinamekidev.maraton.cloud.BucketManager;
import com.sinamekidev.maraton.models.db.SocialPost;
import com.sinamekidev.maraton.models.db.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostOutput {
    private String post_uid;
    private byte[] post_image;
    private String post_text;
    private List<String> post_like;
    private int comment_count;
    private String userName;
    private byte[] user_image;

    public PostOutput(SocialPost socialPost, User user) throws IOException {
        this.post_uid = socialPost.getPost_uid();
        this.post_image = BucketManager.getInstance().readFile(socialPost.getPost_image_url());
        this.post_text = socialPost.getPost_text();
        this.post_like = socialPost.getPost_like_list();
        this.comment_count = socialPost.getComment_list().size();
        this.userName = user.getUserName();
        this.user_image = BucketManager.getInstance().readFile(user.getUser_image_url());
    }
}
