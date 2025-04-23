package com.sinamekidev.maraton.models.outputs;

import com.sinamekidev.maraton.StringUtils;
import com.sinamekidev.maraton.models.db.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CourseDetailOutput {
    private String course_uid;
    private String course_text;
    private byte[] course_image;
    private String teacherName;
    private float rate;
    private int score;
    private List<String> lecture_list;
    public CourseDetailOutput(Course course) throws IOException {
        this.course_uid = course.getCourse_uid();
        this.course_text = course.getCourse_text();
        this.teacherName = course.getTeacherName();
        this.rate = course.getRate();
        this.score = course.getScore();
        this.lecture_list = course.getLecture_list();
        Path pathFile = Path.of(StringUtils.UPLOAD_POINT,course.getCourse_image_url());
        this.course_image = new ByteArrayResource(Files.readAllBytes(pathFile)).getByteArray();
    }
}
