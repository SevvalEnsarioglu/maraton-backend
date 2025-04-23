package com.sinamekidev.maraton.models.db;

import com.sinamekidev.maraton.models.inputs.Register;
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
public class User {
    @Id
    private String user_uid;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String bio;
    private String user_image_url;
    private int score;
    private List<String> owned_course_list;
    private String password;
    public User(Register register){
        this.firstName = register.getFirstName();
        this.lastName = register.getLastName();
        this.userName = register.getUserName();
        this.email = register.getEmail();
        this.password = register.getPassword();
    }
    public User(){
        this.owned_course_list = new ArrayList<>();
    }
}
