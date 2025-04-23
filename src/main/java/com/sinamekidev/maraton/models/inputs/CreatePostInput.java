package com.sinamekidev.maraton.models.inputs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class CreatePostInput {
    private String user_uid;
    private byte[] post_image;
    private String post_text;
}
