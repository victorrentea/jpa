package victor.training.jpa.app.facade.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import victor.training.jpa.app.entity.MoreTeacherDetails;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.entity.Teacher.Grade;

@Data
@NoArgsConstructor
public class TeacherDto {
	private Long id;
	private String name;
	private Grade grade;
	private MoreTeacherDetails moreDetails;
	private TimeSlotDto counselingInterval;
	
	public TeacherDto(Teacher teacher) {
		id = teacher.getId();
		name = teacher.getName();
		grade = teacher.getGrade();
		moreDetails = teacher.getMoreDetails();

		counselingInterval = new TimeSlotDto(teacher.getCounseling());
//		counselingInterval = new TimeSlotDto(
//				teacher.getCounselingDay(),
//				teacher.getCounselingStartHour(),
//				teacher.getCounselingDurationInHours(),
//				teacher.getCounselingRoomId()
//		);
	}
}
