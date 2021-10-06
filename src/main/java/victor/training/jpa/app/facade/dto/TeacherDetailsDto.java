package victor.training.jpa.app.facade.dto;

import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.domain.entity.Teacher.Grade;

import java.time.DayOfWeek;

public class TeacherDetailsDto {

	public Long id;
	public String name;
	public String cv;
	public Grade grade;

	public DayOfWeek day;
	public int startHour;
	public int durationInHours;
	public String roomId;

	public TeacherDetailsDto() {
	}
	
	public TeacherDetailsDto(Teacher teacher) {
		id = teacher.getId();
		name = teacher.getName();
		grade = teacher.getGrade();
		if (teacher.getDetails() != null) {
			cv = teacher.getDetails().getCv();
		}
//		counselingInterval = new TimeSlotDto(
//				teacher.getCounselingDay(),
//				teacher.getCounselingStartHour(),
//				teacher.getCounselingDurationInHours(),
//				teacher.getCounselingRoomId()
//				);
	}
}
