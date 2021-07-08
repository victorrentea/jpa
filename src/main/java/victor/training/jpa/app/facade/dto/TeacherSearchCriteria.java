package victor.training.jpa.app.facade.dto;

import victor.training.jpa.app.domain.entity.Teacher.Grade;

import javax.validation.constraints.NotNull;

public class TeacherSearchCriteria { // dto
   @NotNull
   public String name;
   public Grade grade;
}
