package victor.training.jpa.app.facade.dto;

import lombok.Value;
import victor.training.jpa.app.domain.entity.Teacher.Grade;

@Value
public class TeacherSearchResult {
   long id;
   String name;
   Grade grade;
}
