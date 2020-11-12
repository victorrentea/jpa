package victor.training.jpa.app.facade.dto;

import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.Teacher.Grade;

public class TeacherDetailsDto {

	public Long id;
	public String name;
	public String cv;
	public Grade grade;
	public TimeSlotDto counselingInterval;
	
	public TeacherDetailsDto() {
	}
	
	public TeacherDetailsDto(Teacher teacher) {
		id = teacher.getId();
		name = teacher.getName();
		grade = teacher.getGrade();
		if (teacher.getDetails() != null) {
			cv = teacher.getDetails().getCv();
		}
		counselingInterval = new TimeSlotDto(
				teacher.getCounselingTime().getDay(),
				teacher.getCounselingTime().getStartHour(),
				teacher.getCounselingTime().getDurationInHours(),
				teacher.getCounselingTime().getRoomId()
				);
	}
}
