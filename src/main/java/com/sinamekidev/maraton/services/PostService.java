package com.sinamekidev.maraton.services;

import com.sinamekidev.maraton.cloud.BucketManager;
import com.sinamekidev.maraton.dao.CommentRepository;
import com.sinamekidev.maraton.dao.PostRepository;
import com.sinamekidev.maraton.dao.UserRepository;
import com.sinamekidev.maraton.models.db.SocialPost;
import com.sinamekidev.maraton.models.db.SocialPostComment;
import com.sinamekidev.maraton.models.db.User;
import com.sinamekidev.maraton.models.inputs.CreateCommentInput;
import com.sinamekidev.maraton.models.inputs.CreatePostInput;
import com.sinamekidev.maraton.models.inputs.PostLikeInput;
import com.sinamekidev.maraton.models.outputs.PostDetailCommentOutput;
import com.sinamekidev.maraton.models.outputs.PostDetailOutput;
import com.sinamekidev.maraton.models.outputs.PostOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private MongoTemplate mongoTemplate;
    @Autowired
    public PostService(PostRepository postRepository,
                       CommentRepository commentRepository,
                       UserRepository userRepository,
                       MongoTemplate mongoTemplate){
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }
    public List<PostOutput> getPosts() throws IOException {
        List<SocialPost> postList = this.postRepository.findAll();
        ArrayList<PostOutput> postOutputs = new ArrayList<>();
        for (int i = 0; i < postList.size(); i++) {
            User user = this.userRepository.findById(postList.get(i).getUser_uid()).get();
            postOutputs.add(new PostOutput(postList.get(i),user));
        }
        return postOutputs;
    }
    public PostDetailOutput getPost(String post_uid) throws IOException {
        SocialPost socialPost = postRepository.findById(post_uid).get();
        Query query = new Query(Criteria.where("post_uid").is(post_uid));
        List<SocialPostComment> commentList = mongoTemplate.find(query,SocialPostComment.class);
        ArrayList<PostDetailCommentOutput> commentOutputs = new ArrayList<>();
        for (int i = 0; i < commentList.size(); i++) {
            SocialPostComment comment = commentList.get(i);
            commentOutputs.add(new PostDetailCommentOutput(comment));
        }
        PostDetailOutput postDetailOutput = new PostDetailOutput(socialPost);
        postDetailOutput.setPostDetailCommentOutputs(commentOutputs);
        return postDetailOutput;
    }

    @Transactional
    public boolean createPost(CreatePostInput postInput) throws IOException {
        SocialPost socialPost = new SocialPost();
        socialPost.setPost_uid(UUID.randomUUID().toString());
        socialPost.setPost_text(postInput.getPost_text());
        socialPost.setPost_image_url(BucketManager.getInstance().uploadFile(postInput.getPost_image(),socialPost.getPost_uid()));
        socialPost.setPost_text(postInput.getPost_text());
        socialPost.setUser_uid(postInput.getUser_uid());
        postRepository.insert(socialPost);
        return true;
    }

    @Transactional
    public boolean createComment(CreateCommentInput createCommentInput) {
        SocialPostComment socialPostComment = new SocialPostComment();
        socialPostComment.setUser_uid(createCommentInput.getUser_uid());
        socialPostComment.setPost_uid(createCommentInput.getSocial_post_uid());
        socialPostComment.setComment_text(createCommentInput.getComment_text());
        commentRepository.insert(socialPostComment);
        return true;
    }

    @Transactional
    public boolean likePost(PostLikeInput postLikeInput) {
        User user = userRepository.findById(postLikeInput.getUser_uid()).get();
        SocialPost socialPost = postRepository.findById(postLikeInput.getSocial_post_uid()).get();
        if (user == null || socialPost == null){
            return false;
        }
        List<String> like_list = socialPost.getPost_like_list();
        if (like_list.indexOf(user.getUser_uid()) == -1){
            like_list.add(user.getUser_uid());
        }
        else{
            like_list.remove(user.getUser_uid());
        }
        socialPost.setPost_like_list(like_list);
        postRepository.save(socialPost);
        return true;
    }
}
