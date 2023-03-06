package victor.training.jpa.app.facade.dto;

import victor.training.jpa.app.entity.Teacher.Grade;

public class TeacherSearchCriteria {
   public String name;
   public Grade grade;
   public boolean teachingCourses;

   public String orderBy = "id";
   public Integer pageIndex = 0;
   public Integer pageSize = 10;
}
