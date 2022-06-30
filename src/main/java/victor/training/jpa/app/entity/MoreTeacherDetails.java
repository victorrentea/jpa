package victor.training.jpa.app.entity;

import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
public class MoreTeacherDetails {
    private int age;
    private String nationality;
    private List<String> certifications;
}
