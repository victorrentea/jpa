package victor.training.jpa.app.facade.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import victor.training.jpa.app.entity.MoreTeacherDetails;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.entity.Teacher.Grade;

@Data
@NoArgsConstructor
public class TeacherDetailsDto {
	private Long id;
	private String name;
	private String cv;
	private Grade grade;

	private TimeSlotDto counselingInterval;
	private MoreTeacherDetails moreDetails;
	
	public TeacherDetailsDto(Teacher teacher) {
		id = teacher.getId();
		name = teacher.getName();
		grade = teacher.getGrade();
		if (teacher.getDetails() != null) {
			cv = teacher.getDetails().getCv().toString();
		}
		counselingInterval = new TimeSlotDto(teacher.getCounseling());

//		counselingInterval = new TimeSlotDto(
//				teacher.getCounselingDay(),
//				teacher.getCounselingStartHour(),
//				teacher.getCounselingDurationInHours(),
//				teacher.getCounselingRoomId()
//		);
	}
}
