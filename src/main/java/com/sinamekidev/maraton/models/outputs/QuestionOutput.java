package com.sinamekidev.maraton.models.outputs;

import com.sinamekidev.maraton.StringUtils;
import com.sinamekidev.maraton.models.db.Question;
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
public class QuestionOutput {
    private String question_uuid;
    private byte[] question_image;
    private String question_text;
    private int question_upcount;
    private int question_answer_count;
    private String user_uid;
    private String userName;
    public QuestionOutput(Question question) throws IOException {
        this.question_uuid = question.getQuestion_uid();
        this.question_text = question.getQuestion_text();
        this.question_upcount = question.getQuestion_up_list().size() - question.getQuestion_down_list().size();
        this.question_answer_count = question.getQuestion_answer_list().size();
        this.user_uid = question.getUser_uid();
        Path pathFile = Path.of(StringUtils.UPLOAD_POINT,question.getQuestion_image_url());
        this.question_image = new ByteArrayResource(Files.readAllBytes(pathFile)).getByteArray();
    }
}
