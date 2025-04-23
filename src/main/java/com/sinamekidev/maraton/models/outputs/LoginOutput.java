package com.sinamekidev.maraton.models.outputs;

import com.sinamekidev.maraton.StringUtils;
import com.sinamekidev.maraton.cloud.BucketManager;
import com.sinamekidev.maraton.models.db.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginOutput {
    private String user_uid;
    private String firstName;
    private String lastName;
    private String email;
    private int score;
    private String bio;
    private String userName;
    private byte[] user_image;
    public LoginOutput(User user) throws IOException {
        this.user_uid = user.getUser_uid();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.score = user.getScore();
        this.bio = user.getBio();
        this.userName = user.getUserName();
        this.user_image = BucketManager.getInstance().readFile(user.getUser_image_url());
    }
}
