package victor.training.jpa.app.facade.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.entity.Teacher.Grade;

@Data
@NoArgsConstructor
public class TeacherDto {
	private Long id;
	private String name;
	private Grade grade;
	
	public TeacherDto(Teacher teacher) {
		id = teacher.getId();
		name = teacher.getName();
		grade = teacher.getGrade();
	}
}
