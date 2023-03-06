package victor.training.jpa.app.facade.dto;

import lombok.Value;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.entity.Teacher.Grade;

@Value
public class TeacherSearchResult {
   long id;
   String name;
   Grade grade;
   String cv;

   public TeacherSearchResult(long id, String name, Grade grade, String cv) {
      this.id = id;
      this.name = name;
      this.grade = grade;
      this.cv = cv;
   }
   public TeacherSearchResult(Teacher teacher) {
      this.id = teacher.getId();
      this.name = teacher.getName();
      this.grade = teacher.getGrade();
      this.cv = teacher.getDetails() != null ?
            teacher.getDetails().getCv() : "N/A";
   }
}
