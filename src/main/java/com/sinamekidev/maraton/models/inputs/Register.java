package com.sinamekidev.maraton.models.inputs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Register {
    private String email;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
}
