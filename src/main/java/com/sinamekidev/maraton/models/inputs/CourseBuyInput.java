package com.sinamekidev.maraton.models.inputs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CourseBuyInput {
    private String user_uid;
    private String course_uid;
}
